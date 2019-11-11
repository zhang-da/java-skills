package com.da.learn.netty.protocol;

import com.da.learn.netty.protocol.command.CommandEnum;
import com.da.learn.netty.protocol.request.LoginRequestPacket;
import com.da.learn.netty.serialize.Serializer;
import com.da.learn.netty.serialize.SerializerChooser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.Objects;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;

    public static ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        // 1. 创建 ByteBuf 对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public static void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public static Packet decode(ByteBuf byteBuf) {
        int magicNum = byteBuf.readInt();
        if (!Objects.equals(MAGIC_NUMBER, magicNum)) {
            System.out.println("magic_num有误");
            return null;
        }
        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        CommandEnum commandEnum = CommandEnum.getCommandEnumByCommand(command);
        if (commandEnum == null) {
            return null;
        }
        Class<? extends Packet> requestType = commandEnum.getPacketClazz();
        Serializer serializer = SerializerChooser.getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;

    }


    public static void main(String[] args) {

        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setVersion((byte) 1);
        packet.setUserId("1");
        packet.setUsername("da");
        packet.setPassword("password");
        ByteBuf encode = PacketCodeC.encode(ByteBufAllocator.DEFAULT, packet);
        Packet decode = PacketCodeC.decode(encode);
        System.out.println(decode);

    }
}

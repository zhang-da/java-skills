package com.da.learn.netty.protocol.command;

import com.da.learn.netty.protocol.request.LoginRequestPacket;
import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.request.MessageRequestPacket;
import com.da.learn.netty.protocol.response.LoginResponsePacket;
import com.da.learn.netty.protocol.response.MessageResponsePacket;

public enum CommandEnum {
    LOGIN_REQUEST((byte) 1, LoginRequestPacket.class),
    LOGIN_RESPONSE((byte) 2, LoginResponsePacket.class),
    MESSAGE_REQUEST((byte) 3, MessageRequestPacket.class),
    MESSAGE_RESPONSE((byte) 4, MessageResponsePacket.class);

    private Byte command;
    private Class<? extends Packet> packetClazz;

    CommandEnum(Byte command, Class<? extends Packet> packetClazz) {
        this.command = command;
        this.packetClazz = packetClazz;
    }

    public Byte getCommand() {
        return command;
    }

    public Class<? extends Packet> getPacketClazz() {
        return packetClazz;
    }

    public static CommandEnum getCommandEnumByCommand(Byte command) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.getCommand().equals(command)) {
                return commandEnum;
            }
        }
        return null;
    }
}

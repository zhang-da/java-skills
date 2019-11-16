package com.da.learn.netty.protocol.command;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.request.*;
import com.da.learn.netty.protocol.response.*;
import com.da.learn.netty.server.handler.*;
import io.netty.channel.SimpleChannelInboundHandler;

public enum CommandEnum {
    LOGIN_REQUEST((byte) 1, LoginRequestPacket.class, null),
    LOGIN_RESPONSE((byte) 2, LoginResponsePacket.class, null),
    MESSAGE_REQUEST((byte) 3, MessageRequestPacket.class, MessageRequestHandler.INSTANCE),
    MESSAGE_RESPONSE((byte) 4, MessageResponsePacket.class, null),
    LOGOUT_REQUEST((byte) 5, LogoutRequestPacket.class, LogoutRequestHandler.INSTANCE),
    LOGOUT_RESPONSE((byte) 6, LogoutResponsePacket.class, null),
    CREATE_GROUP_REQUEST((byte) 7, CreateGroupRequestPacket.class, CreateGroupRequestHandler.INSTANCE),
    CREATE_GROUP_RESPONSE((byte) 8, CreateGroupResponsePacket.class, null),
    LIST_GROUP_MEMBERS_REQUEST((byte) 9, ListGroupMembersRequestPacket.class, ListGroupMembersRequestHandler.INSTANCE),
    LIST_GROUP_MEMBERS_RESPONSE((byte) 10, ListGroupMembersResponsePacket.class, null),
    JOIN_GROUP_REQUEST((byte) 11, JoinGroupRequestPacket.class, JoinGroupRequestHandler.INSTANCE),
    JOIN_GROUP_RESPONSE((byte) 12, JoinGroupResponsePacket.class, null),
    QUIT_GROUP_REQUEST((byte) 13, QuitGroupRequestPacket.class, QuitGroupRequestHandler.INSTANCE),
    QUIT_GROUP_RESPONSE((byte) 14, QuitGroupResponsePacket.class, null),
    GROUP_MESSAGE_REQUEST((byte)15, GroupMessageRequestPacket.class, GroupMessageRequestHandler.INSTANCE),
    GROUP_MESSAGE_RESPONSE((byte)16, GroupMessageResponsePacket.class, null);

    private Byte command;
    private Class<? extends Packet> packetClazz;
    private SimpleChannelInboundHandler<? extends Packet> handler;

    CommandEnum(Byte command, Class<? extends Packet> packetClazz, SimpleChannelInboundHandler<? extends Packet> handler) {
        this.command = command;
        this.packetClazz = packetClazz;
        this.handler = handler;
    }

    public Byte getCommand() {
        return command;
    }

    public Class<? extends Packet> getPacketClazz() {
        return packetClazz;
    }

    public SimpleChannelInboundHandler<? extends Packet> getHandler() {
        return handler;
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

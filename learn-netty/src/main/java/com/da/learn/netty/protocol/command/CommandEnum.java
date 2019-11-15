package com.da.learn.netty.protocol.command;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.request.*;
import com.da.learn.netty.protocol.response.*;

public enum CommandEnum {
    LOGIN_REQUEST((byte) 1, LoginRequestPacket.class),
    LOGIN_RESPONSE((byte) 2, LoginResponsePacket.class),
    MESSAGE_REQUEST((byte) 3, MessageRequestPacket.class),
    MESSAGE_RESPONSE((byte) 4, MessageResponsePacket.class),
    LOGOUT_REQUEST((byte) 5, LogoutRequestPacket.class),
    LOGOUT_RESPONSE((byte) 6, LogoutResponsePacket.class),
    CREATE_GROUP_REQUEST((byte) 7, CreateGroupRequestPacket.class),
    CREATE_GROUP_RESPONSE((byte) 8, CreateGroupResponsePacket.class),
    LIST_GROUP_MEMBERS_REQUEST((byte) 9, ListGroupMembersRequestPacket.class),
    LIST_GROUP_MEMBERS_RESPONSE((byte) 10, ListGroupMembersResponsePacket.class),
    JOIN_GROUP_REQUEST((byte) 11, JoinGroupRequestPacket.class),
    JOIN_GROUP_RESPONSE((byte) 12, JoinGroupResponsePacket.class),
    QUIT_GROUP_REQUEST((byte) 13, QuitGroupRequestPacket.class),
    QUIT_GROUP_RESPONSE((byte) 14, QuitGroupResponsePacket.class),
    GROUP_MESSAGE_REQUEST((byte)15, GroupMessageRequestPacket.class),
    GROUP_MESSAGE_RESPONSE((byte)16, GroupMessageResponsePacket.class);

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

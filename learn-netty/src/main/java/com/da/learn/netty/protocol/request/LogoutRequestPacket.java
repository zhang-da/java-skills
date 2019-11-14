package com.da.learn.netty.protocol.request;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return CommandEnum.LOGOUT_REQUEST.getCommand();
    }
}

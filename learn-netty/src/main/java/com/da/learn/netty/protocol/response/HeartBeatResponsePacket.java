package com.da.learn.netty.protocol.response;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return CommandEnum.HEARTBEAT_RESPONSE.getCommand();
    }
}

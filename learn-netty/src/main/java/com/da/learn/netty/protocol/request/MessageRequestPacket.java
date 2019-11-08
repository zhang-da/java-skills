package com.da.learn.netty.protocol.request;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return CommandEnum.MESSAGE_REQUEST.getCommand();
    }
}

package com.da.learn.netty.protocol.response;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return CommandEnum.MESSAGE_RESPONSE.getCommand();
    }
}

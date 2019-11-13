package com.da.learn.netty.protocol.request;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestPacket extends Packet {

    private String toUserId;

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return CommandEnum.MESSAGE_REQUEST.getCommand();
    }
}

package com.da.learn.netty.protocol.response;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import com.da.learn.netty.session.Session;
import lombok.Data;

@Data
public class GroupMessageResponsePacket extends Packet {

    private Boolean success;

    private String reason;

    private String groupId;

    private String message;

    private Session fromUser;

    @Override
    public Byte getCommand() {
        return CommandEnum.GROUP_MESSAGE_RESPONSE.getCommand();
    }
}

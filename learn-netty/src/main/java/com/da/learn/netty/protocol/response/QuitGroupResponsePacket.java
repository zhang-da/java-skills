package com.da.learn.netty.protocol.response;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import lombok.Data;

@Data
public class QuitGroupResponsePacket extends Packet {

    private Boolean success;
    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return CommandEnum.QUIT_GROUP_RESPONSE.getCommand();
    }
}

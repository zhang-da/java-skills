package com.da.learn.netty.protocol.request;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import lombok.Data;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> UserIdList;

    @Override
    public Byte getCommand() {
        return CommandEnum.CREATE_GROUP_REQUEST.getCommand();
    }
}

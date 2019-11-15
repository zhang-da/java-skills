package com.da.learn.netty.protocol.request;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {
    private String groupId;
    private String message;

    @Override
    public Byte getCommand() {
        return CommandEnum.GROUP_MESSAGE_REQUEST.getCommand();
    }
}

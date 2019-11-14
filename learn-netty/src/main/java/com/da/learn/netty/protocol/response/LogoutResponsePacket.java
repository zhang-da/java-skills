package com.da.learn.netty.protocol.response;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import lombok.Data;

@Data
public class LogoutResponsePacket extends Packet {

    private Boolean success;

    @Override
    public Byte getCommand() {
        return CommandEnum.LOGOUT_RESPONSE.getCommand();
    }
}

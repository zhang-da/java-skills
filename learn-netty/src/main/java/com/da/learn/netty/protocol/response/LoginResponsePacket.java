package com.da.learn.netty.protocol.response;

import com.da.learn.netty.protocol.Packet;
import com.da.learn.netty.protocol.command.CommandEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponsePacket extends Packet {

    private Boolean success;

    private String reason;

    private String userId;

    private String userName;

    @Override
    public Byte getCommand() {
        return CommandEnum.LOGIN_RESPONSE.getCommand();
    }
}

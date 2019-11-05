package com.da.learn.netty.codectest;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
    }


    /**
     * 指令
     */
    public abstract Byte getCommand();

    public static Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}

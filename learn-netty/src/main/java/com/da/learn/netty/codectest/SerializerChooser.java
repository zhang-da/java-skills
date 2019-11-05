package com.da.learn.netty.codectest;

import java.util.HashMap;
import java.util.Map;

public final class SerializerChooser {
    private static final Map<Byte, Serializer> serializerMap;

    static {
        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public static Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }
}

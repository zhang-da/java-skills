package com.da.learn.utils.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XmlUtilJackson {
    private static final ObjectMapper OBJECT_MAPPER = new XmlMapper();

    static {
        //反序列化时，若实体类没有对应的属性，是否抛出JsonMappingException异常，false忽略掉
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //序列化是否绕根元素，true，则以类名为根元素
        OBJECT_MAPPER.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        //忽略空属性
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //XML标签名:使用骆驼命名的属性名，
        OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
    }

    /**
     * 将Object转换为XML字符串
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String Object2XmlString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 将XML字符串转换为JavaBean对象，ObjectMapper还提供了很多重载方法，详情查看源码，这里不一一列举
     *
     * @param content
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlString2Object(String content, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(content, tClass);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T xmlString2Object(String content, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

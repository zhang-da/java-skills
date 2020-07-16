package com.da.learn.flink.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper mapperExcludeNull = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        mapperExcludeNull.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapperExcludeNull.setSerializationInclusion(Include.NON_NULL);
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return toJson(obj, mapper);
    }

    private static String toJson(Object obj, ObjectMapper objectMapper) throws JsonProcessingException {
        if (obj == null)
            return null;
        return objectMapper.writeValueAsString(obj);
    }

    public static String toJsonIgnoreNull(Object obj) throws JsonProcessingException {
        return toJson(obj, mapperExcludeNull);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || "".equals(json)) {
            return null;
        }
        T t = null;
        try {
            t = mapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return t;
    }

    public static <T> T fromJson(InputStream is, Class<T> clazz) {
        if (is == null)
            return null;

        T t = null;
        try {
            t = mapper.readValue(is, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return t;
    }

    public static <T> T fromJson(byte[] src, Class<T> clazz) {
        if (src == null)
            return null;

        T t = null;
        try {
            t = mapper.readValue(src, clazz);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return t;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) throws IOException {
        return fromJson(json, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> toList(String json) throws IOException {
        return fromJson(json, List.class);
    }

    public static <T> List<T> toList(String jsonArray, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonArray, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> T fromJson(String json, JavaType javaType) throws IOException {
        return mapper.readValue(json, javaType);
    }

    /**
     * 从json字符串反序列化到bean
     *
     * @param jsonString
     * @param valueTypeRef
     * @return
     * @author: Created on 2016年3月7日 上午11:19:52
     * @modify author:修改人
     * Modify on 修改时间
     */
    public static <T> T fromJson(String jsonString, TypeReference<?> valueTypeRef) {
        try {
            T result = (T) mapper.readValue(jsonString, valueTypeRef);
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException("Deserialize from JSON failed.", e);
        }
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param clazz 要转化的类型
     * @param map   包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException    如果分析类属性失败
     * @throws IllegalAccessException    如果实例化 JavaBean 失败
     * @throws InstantiationException    如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static <T> T fromMap(Map map, Class<T> clazz) {
        T obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            obj = clazz.newInstance(); // 创建 JavaBean 对象
            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);
                    if ("".equals(value)) {
                        value = null;
                    }
                    Object[] args = new Object[1];
                    args[0] = value;
                    try {
                        descriptor.getWriteMethod().invoke(obj, args);
                    } catch (InvocationTargetException e) {
                        System.out.println("字段映射失败");
                    }
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.println("实例化 JavaBean 失败");
        } catch (IntrospectionException e) {
            System.out.println("分析类属性失败");
        } catch (IllegalArgumentException e) {
            System.out.println("映射错误");
        }
        return (T) obj;
    }

    public static void main(String[] args) {
        boolean success = false;
        String totalTime = null;
        String test= "finish={\"module\":\"1.1\",\"testResultFlag\":0,\"executionTime\":3.99ms}";
        if (test.contains("finish=")) {
            String[] temps1 = test.split(",");
            String resultStr = temps1[1].split(":")[1].trim();
            success = "0".equals(resultStr);
            String timeStr = temps1[2].split(":")[1].trim();
            totalTime = timeStr.substring(0, timeStr.length() - 3);

        }
        System.out.println(success);
        System.out.println(totalTime);


        String test2 = "[*]Sending packets 5/16";
        if (test2.contains("Sending packets")) {
            String[] sendingStr = test2.split(" ");
            int length = sendingStr.length;
            String packs = sendingStr[length - 1];
            System.out.println(packs.split("/")[0].trim());
        }
    }

}

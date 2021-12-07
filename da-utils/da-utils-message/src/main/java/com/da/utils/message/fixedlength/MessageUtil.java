package com.da.utils.message.fixedlength;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    public static final String DEFAULT_LINE_SEPARATOR = "\r\n";
    public static final String ALL_IN_ONE_LINE_SEPARATOR = "";

    public static <T> T messageToBean(String message, Class<T> clazz, Class<?> bodyClazz, String charsetName) throws MessageException {
        try {
            return messageToBean(message.getBytes(charsetName), clazz, bodyClazz, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new MessageException("convert error");
        }
    }

    public static <T> T messageToBean(String message, Class<T> clazz, Class<?> bodyClazz, String charsetName, boolean allInOneLine) throws MessageException {
        try {
            return messageToBean(message.getBytes(charsetName), clazz, bodyClazz, charsetName, allInOneLine);
        } catch (UnsupportedEncodingException e) {
            throw new MessageException("convert error");
        }
    }

    public static <T> T messageToBean(byte[] msgBytes, Class<T> clazz, Class<?> bodyClazz, String charsetName) throws MessageException {
        return messageToBean(msgBytes, clazz, bodyClazz, charsetName, false);
    }

    public static <T> T messageToBean(byte[] msgBytes, Class<T> clazz, Class<?> bodyClazz, String charsetName, boolean allInOneLine) throws MessageException {
        TuplePair<Field, Field> headerAndBodyField = MessageBeanUtil.getHeaderAndBodyField(clazz);
        Field headerField = headerAndBodyField.getV1();
        Field bodyField = headerAndBodyField.getV2();

        T msgBean = null;
        try {
            msgBean = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new MessageException("bean class define error");
        }


        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(msgBytes);
                BufferedReader br = new BufferedReader(new InputStreamReader(byteArrayInputStream, charsetName))
        ) {
            String line;
            // 处理头
            if (allInOneLine) {
                int lineSize = lineSize(headerField.getType());
                line = readLine(byteArrayInputStream, lineSize, charsetName);
            } else {
                line = readLine(br);
            }
            PropertyDescriptor pdHeader = new PropertyDescriptor(headerField.getName(), clazz);
            pdHeader.getWriteMethod().invoke(msgBean, MessagePackUtil.unPackOneLine(headerField.getType(), line, charsetName));

            //处理消息体
            if (bodyClazz != null && bodyField != null) {
                PropertyDescriptor pdBody = new PropertyDescriptor(bodyField.getName(), clazz);
                List<Object> bodyList = new ArrayList<>();
                int lineSize = 0;
                if (allInOneLine) {
                    lineSize = lineSize(bodyClazz);
                }
                while ((line = allInOneLine ? readLine(byteArrayInputStream, lineSize, charsetName) : readLine(br)) != null) {
                    Object o = MessagePackUtil.unPackOneLine(bodyClazz, line, charsetName);
                    bodyList.add(o);
                }
                pdBody.getWriteMethod().invoke(msgBean, bodyList);
            }
        } catch (IOException | IllegalAccessException | InstantiationException | IntrospectionException | InvocationTargetException e) {
            throw new MessageException("convert error");
        }
        return msgBean;
    }

    public static String beanToMessage(Object object, String charsetName) throws MessageException {
        return beanToMessage(object, charsetName, DEFAULT_LINE_SEPARATOR);
    }

    public static String beanToMessage(Object object, String charsetName, String lineSeparator) throws MessageException {
        TuplePair<Field, Field> headerAndBodyField = MessageBeanUtil.getHeaderAndBodyField(object.getClass());
        Field headerField = headerAndBodyField.getV1();
        Field bodyField = headerAndBodyField.getV2();
        StringBuilder sb = new StringBuilder();
        try {
            // 处理消息头
            if (!headerField.isAccessible()) {
                headerField.setAccessible(true);
            }
            Object headerObj = headerField.get(object);
            sb.append(MessagePackUtil.packOneLine(headerObj, charsetName, lineSeparator));

            //处理消息体
            if (bodyField != null) {
                if (!bodyField.isAccessible()) {
                    bodyField.setAccessible(true);
                }
                Object bodyObj = bodyField.get(object);
                if (bodyObj == null) {
                    sb.append(lineSeparator);
                } else {
                    ((Iterable) bodyObj).forEach(o -> {
                        sb.append(MessagePackUtil.packOneLine(o, charsetName, lineSeparator));
                    });
                }
            }
        } catch (IllegalAccessException e) {
            throw new MessageException("bean get error");
        }
        return sb.toString();
    }

    private static int lineSize(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            return 0;
        }
        Map<Integer, TuplePair<Field, MsgField>> fieldMap = MessageBeanUtil.getAnnotationFieldMap(fields);
        if (fieldMap.size() == 0) {
            return 0;
        }
        int size = 0;
        for (TuplePair<Field, MsgField> mapValue : fieldMap.values()) {
            Field field = mapValue.getV1();
            MsgField msgField = mapValue.getV2();
            size += msgField.length();
        }
        return size;
    }


    private static String readLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private static String readLine(ByteArrayInputStream byteArrayInputStream, int size, String charsetName) throws IOException {

        byte[] bytes = new byte[size];
        int read = byteArrayInputStream.read(bytes);
        if (read != -1) {
            return new String(bytes, charsetName);
        }
        return null;
    }


}

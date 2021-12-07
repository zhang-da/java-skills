package com.da.utils.message.fixedlength;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Map;

public class MessagePackUtil {
    /**
     * 组装一行消息
     *
     * @param object
     * @param charsetName
     * @return
     */
    public static String packOneLine(Object object, String charsetName, String lineSeparator) {
        if (object == null) {
            return lineSeparator;
        }
        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            //没有字段
            return lineSeparator;
        }
        Map<Integer, TuplePair<Field, MsgField>> fieldMap = MessageBeanUtil.getAnnotationFieldMap(fields);
        if (fieldMap.size() == 0) {
            //没有MsgField注解字段
            return lineSeparator;
        }
        StringBuilder sb = new StringBuilder();
        fieldMap.forEach((position, fieldMsgFieldTuplePair) -> {
            Field field = fieldMsgFieldTuplePair.getV1();
            MsgField msgField = fieldMsgFieldTuplePair.getV2();
            Object fieldValue = null;
            try {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
                return;
            }
            String strToFill = fieldValue == null ? msgField.nullValue() : fieldValue.toString();
            sb.append(fillOneField(strToFill, msgField.fillSide(), msgField.fillChar(), msgField.length(), charsetName));
        });
        sb.append(lineSeparator);
        return sb.toString();
    }


    /**
     * string消息转bean
     *
     * @param clazz
     * @param line
     * @param charsetName
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws UnsupportedEncodingException
     */
    public static <T> T unPackOneLine(Class<T> clazz, String line, String charsetName) throws IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        if (line == null || line.length() == 0) {
            return null;
        }
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            return null;
        }

        Map<Integer, TuplePair<Field, MsgField>> fieldMap = MessageBeanUtil.getAnnotationFieldMap(fields);
        if (fieldMap.size() == 0) {
            return null;
        }
        byte[] lineBytes = line.getBytes(charsetName);
        int index = 0;
        T lineObj = clazz.newInstance();
        for (TuplePair<Field, MsgField> mapValue : fieldMap.values()) {
            Field field = mapValue.getV1();
            MsgField msgField = mapValue.getV2();
            String value = null;
            try {
                value = new String(lineBytes, index, msgField.length(), charsetName);
                value = getOneFieldRealValue(value, msgField.fillSide(), msgField.fillChar(), msgField.nullValue());
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                pd.getWriteMethod().invoke(lineObj, value);
            } catch (Exception ignored) {
            } finally {
                index += msgField.length();
            }
        }
        return lineObj;
    }

    /**
     * 填充一个字段
     *
     * @param value       待填充值
     * @param fillSide    默认字符填充在哪侧
     * @param fillChar    填充的默认字符
     * @param length      长度
     * @param charsetName 编码
     * @return
     */
    private static String fillOneField(String value, FillSide fillSide, char fillChar, int length, String charsetName) {
        if (value == null) {
            value = "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            byte[] valueBytes = value.getBytes(charsetName);
            if (valueBytes.length > length) {
                byte[] data = new byte[length];
                System.arraycopy(valueBytes, 0, data, 0, length);
                sb.append(new String(data, charsetName));
                return sb.toString();
            }
            if (fillSide == FillSide.RIGHT) {
                sb.append(value);
            }
            for (int i = valueBytes.length; i < length; i++) {
                sb.append(fillChar);
            }
            if (fillSide == FillSide.LEFT) {
                sb.append(value);
            }
        } catch (UnsupportedEncodingException e) {
            throw new MessageException("not support charsetName: " + charsetName);
        }
        return sb.toString();
    }

    /**
     * 去掉填充字符获取字段真实值
     *
     * @param value     填充后的值
     * @param fillSide  默认字符填充在哪侧
     * @param fillChar  填充的默认字符
     * @param nullValue 如果字段为空时的默认值
     * @return
     */
    private static String getOneFieldRealValue(String value, FillSide fillSide, char fillChar, String nullValue) {
        if (value.length() == 0) {
            return null;
        }
        char[] chars = value.toCharArray();
        if (FillSide.LEFT == fillSide) {
            int index = 0;
            for (int i = 0; i < chars.length; i++) {
                if (fillChar == chars[i]) {
                    index++;
                } else {
                    break;
                }
            }
            String realValue = value.substring(index);
            return nullValue.equals(realValue) ? null : realValue;
        } else if (FillSide.RIGHT == fillSide) {
            int index = chars.length - 1;
            for (int i = index; i >= 0; i--) {
                if (fillChar == chars[i]) {
                    index--;
                } else {
                    break;
                }
            }
            String realValue = value.substring(0, index + 1);
            return nullValue.equals(realValue) ? null : realValue;
        } else {
            throw new MessageException("FillSide not support");
        }
    }


}

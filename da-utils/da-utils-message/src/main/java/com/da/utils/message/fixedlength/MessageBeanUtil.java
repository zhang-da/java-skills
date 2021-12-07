package com.da.utils.message.fixedlength;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

public class MessageBeanUtil {

    /**
     * 获取注解的field字段
     *
     * @param fields
     * @return
     */
    public static Map<Integer, TuplePair<Field, MsgField>> getAnnotationFieldMap(Field[] fields) {
        Map<Integer, TuplePair<Field, MsgField>> fieldMap = new TreeMap<>();
        for (Field field : fields) {
            MsgField[] msgFields = field.getAnnotationsByType(MsgField.class);
            if (msgFields.length == 0) {
                continue;
            }
            int position = msgFields[0].position();
            fieldMap.put(position, new TuplePair<>(field, msgFields[0]));
        }
        return fieldMap;
    }


    /**
     * 获取头Field和BodyFieldList
     *
     * @param clazz
     * @return
     */
    public static TuplePair<Field, Field> getHeaderAndBodyField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            throw new MessageException("need header and body");
        }
        // 获取header和body
        int headerNum = 0;
        int bodyNum = 0;
        Field headerField = null;
        Field bodyField = null;
        for (Field field : fields) {
            Header[] headerAnnotations = field.getAnnotationsByType(Header.class);
            if (headerAnnotations.length > 0) {
                headerNum += headerAnnotations.length;
                headerField = field;
            }
            Body[] bodyAnnotations = field.getAnnotationsByType(Body.class);
            if (bodyAnnotations.length > 0) {
                bodyNum += bodyAnnotations.length;
                bodyField = field;
            }
        }
        if (headerNum != 1) {
            throw new MessageException("need one header");
        }
        if (bodyNum == 1 && !Iterable.class.isAssignableFrom(bodyField.getType())) {
            throw new MessageException("need Iterable body");
        }
        if (bodyNum > 1) {
            throw new MessageException("at most one body");
        }
        return new TuplePair<>(headerField, bodyField);
    }
}

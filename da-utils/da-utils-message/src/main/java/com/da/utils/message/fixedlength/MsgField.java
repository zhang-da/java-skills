package com.da.utils.message.fixedlength;

import java.lang.annotation.*;

/**
 * 暂时智能放在String上
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MsgField {
    /**
     * 长度
     * @return
     */
    int length();

    /**
     * 位置
     * @return
     */
    int position();

    /**
     * 填充位置
     * 默认字符填充在值得哪侧
     * @return
     */
    FillSide fillSide() default FillSide.RIGHT;

    /**
     * 填充的字符，默认为空格
     * @return
     */
    char fillChar() default ' ';

    /**
     * 字段为空时字段内容
     * @return
     */
    String nullValue() default "";

}

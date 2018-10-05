package com.ly.ems.common.utils.file;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)/*运行时有效*/
@Target(ElementType.FIELD)/*范围*/
public @interface ExcelAttribute {

    /**
     * Excel中的列名内容
     *
     * @return
     */
    String content() default "";


    /**
     * 额外参数中对应的key
     *
     * @return
     */
    String paramKey() default "";

    /**
     * 标题列所占的区域，如："0,0,A,Z" 表示从第一行到第一行，第A列到第Z列都为这个列的区域
     *
     * @return
     */
    String titleRegion() default "";

    /**
     * 字段所在的列，如：A,B,C,D...。与titleRegion的区别是一个是title，一个是内容行
     *
     * @return
     */
    String column() default "";

    /**
     * 是否为纯标题列，是的话就不会从结果集中查找该列数据
     *
     * @return
     */
    boolean isPureTitle() default false;
}
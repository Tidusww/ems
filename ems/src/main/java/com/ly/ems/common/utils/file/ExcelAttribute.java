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
     * 是否为纯标题列，是的话就不会从结果集中查找该列数据
     *
     * @return
     */
    boolean isTitle() default false;
    /**
     * 标题列所占的区域，标题列才会使用，如："1,1,A,Z" 表示从第一行到第一行，第A列到第Z列都为这个列的区域
     *
     * @return
     */
    String titleRegion() default "";

    /**
     * 列名对应的A,B,C,D...,不指定按照默认顺序排序（非标题列才需要指定）
     *
     * @return
     */
    String column() default "";

}
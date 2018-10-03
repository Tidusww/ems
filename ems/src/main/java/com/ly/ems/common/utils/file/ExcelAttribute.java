package com.ly.ems.common.utils.file;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)/*运行时有效*/
@Target(ElementType.FIELD)/*范围*/
public @interface ExcelAttribute {

    /**
     * Excel中的列名
     *
     * @return
     */
    String name();

    /**
     * 列名对应的A,B,C,D...,不指定按照默认顺序排序
     *
     * @return
     */
    String column() default "";

}
package com.ly.ems.common.utils.file;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)/*运行时有效*/
@Target({ElementType.TYPE, ElementType.FIELD})/*范围*/
public @interface ExcelAttribute {

    /**
     * 列表数据的起始行，0为第一行
     * @return
     */
    int contentRow() default 0;

    /**
     * 固定单元内容的静态值
     *
     * @return
     */
    String content() default "";
    /**
     * 固定单元内容在param中对应的key（优先级高于content）
     *
     * @return
     */
    String key() default "";
    /**
     * 固定单元内容所在的单元格，如："0,A" 表示第一行第一列
     *
     * @return
     */
    String region() default "";

    /**
     * 列表字段所在的列，如：A,B,C,D...
     *
     * @return
     */
    String column() default "";
    /**
     * 是否为列表中的列，是的话才会从结果集中查找数据
     *
     * @return
     */
    boolean isColumn() default true;

    /**
     * 标题列所占的区域，如："0,0,A,Z" 表示从第一行到第一行，第A列到第Z列都为这个列的区域
     *
     * @return
     */
    @Deprecated
    String titleRegion() default "";

}
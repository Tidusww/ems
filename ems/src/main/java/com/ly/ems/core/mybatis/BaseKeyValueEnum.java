package com.ly.ems.core.mybatis;


/**
 * Key 是 int
 * Value 是 String
 * 的枚举
 */
public interface BaseKeyValueEnum {

    Integer getKey();
    void setKey(Integer key);
    String getValue();
    void setValue(String value);

}

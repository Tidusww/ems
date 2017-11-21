package com.ly.ems.core.mybatis;

/**
 * Code , Value 都是 String 的枚举
 */
public interface BaseCodeValueEnum {

    String getCode();
    void setCode(String key);
    String getValue();
    void setValue(String value);

}

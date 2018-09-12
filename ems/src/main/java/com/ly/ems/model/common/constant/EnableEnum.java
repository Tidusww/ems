package com.ly.ems.model.common.constant;


import com.fasterxml.jackson.annotation.JsonValue;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;

public enum EnableEnum implements BaseKeyValueEnum {
    /**
     * 状态：
     * 0: 禁用
     * 1: 启用
     */
    DISABLED(0, "禁用"),
    ENABLED(1, "启用");

    private Integer key;
    private String value;

    EnableEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @JsonValue
    @Override
    public Integer getKey() {
        return key;
    }
    @Override
    public void setKey(Integer key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }
    @Override
    public void setValue(String value) {
        this.value = value;
    }


}

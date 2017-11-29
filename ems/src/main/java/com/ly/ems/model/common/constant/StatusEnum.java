package com.ly.ems.model.common.constant;


import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnum implements BaseKeyValueEnum {
    /**
     * 状态：
     * 0: 未知
     * 1: 有效
     * 2: 无效
     */
    UNKNOWN(0, "未知"),
    ACTIVED(1, "有效"),
    DISABLED(2, "无效");

    private Integer key;
    private String value;

    StatusEnum(Integer key, String value) {
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

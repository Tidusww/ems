package com.ly.ems.model.common.constant;


import com.fasterxml.jackson.annotation.JsonValue;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;

public enum YesNoEnum implements BaseKeyValueEnum {
    /**
     * 状态：
     * 0: 否
     * 1: 是
     */
    NO(0, "否"),
    YES(1, "是");

    private Integer key;
    private String value;

    YesNoEnum(Integer key, String value) {
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

package com.ly.ems.model.base.employee.constant;


import com.fasterxml.jackson.annotation.JsonValue;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;

public enum LocationEnum implements BaseKeyValueEnum {
    /**
     * 地区：
     * 0: 本地
     * 1: 外地
     */
    LOCAL(1, "本地"),
    OUTLAND(2, "外地");

    private Integer key;
    private String value;

    LocationEnum(Integer key, String value) {
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

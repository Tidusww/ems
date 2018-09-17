package com.ly.ems.model.base.project.constant;


import com.fasterxml.jackson.annotation.JsonValue;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;

public enum ProjectStatusEnum implements BaseKeyValueEnum {
    /**
     * 状态：
     * 0: 未知
     * 1: 有效
     * 2: 无效
     */
    UNKNOWN(0, "未知"),
    ACTIVE(1, "有效"),
    DISABLED(2, "无效");

    private Integer key;
    private String value;

    ProjectStatusEnum(Integer key, String value) {
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

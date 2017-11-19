package com.liyuan.ems.model.common.constant;


import com.liyuan.ems.core.mybatis.BaseKeyValueEnum;
import org.codehaus.jackson.annotate.JsonValue;

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

    @Override
    public Integer getKey() {
        return key;
    }
    @Override
    public void setKey(Integer key) {
        this.key = key;
    }

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }
    @Override
    public void setValue(String value) {
        this.value = value;
    }


}

package com.ly.ems.model.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;

/**
 * Created by tidus on 2017/12/7.
 */
public enum FileTypeEnum implements BaseKeyValueEnum{

    UNKNOWN(0, "未知"),
    IMAGE(1, "图片"),
    AUDIO(2, "音频"),
    VIDEO(3, "视频");

    private Integer key;
    private String value;

    FileTypeEnum(Integer key, String value) {
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


package com.ly.ems.model.condition.constants;

import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * Created by tidus on 2017/9/12.
 */
public enum ConditionDatasource implements BaseKeyValueEnum {

    /**
     * 数据库:
     * 0: EMS数据库
     */
    EMS(0, "EMS数据库");

    private Integer key;
    private String value;

    ConditionDatasource(Integer key, String value) {
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

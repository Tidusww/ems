package com.liyuan.ems.model.condition.constants;

import com.liyuan.ems.core.mybatis.BaseKeyValueEnum;
import org.codehaus.jackson.annotate.JsonValue;

/**
 * Created by tidus on 2017/9/12.
 */
public enum ConditionType implements BaseKeyValueEnum{


    /**
     * 条件类型：
     * 0: 文本框
     * 1: 下拉框
     * 2: 按钮
     * 3: 时间选择
     * 4: 时间范围
     */
    INPUT(0, "INPUT"),
    SELECT(1, "SELECT"),
    BUTTON(2, "BUTTON"),
    DATE(3, "DATE"),
    RANGE(4, "RANGE");

    private Integer key;
    private String value;

    ConditionType(Integer key, String value) {
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

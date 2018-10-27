package com.ly.ems.model.config.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;

/**
 * Created by tidus on 2018/10/3.
 */
public enum SystemConfigTypeEnum implements BaseCodeValueEnum {

    HOLIDAY("HOLIDAY", "节假日"),
    OVERTIME_DAY("OVERTIME_DAY", "强制上班日"),
    HOT_ALLOWANCE_BEGIN_MONTH("HOT_ALLOWANCE_BEGIN_MONTH", "高温补贴发放开始月份（含）"),
    HOT_ALLOWANCE_END_MONTH("HOT_ALLOWANCE_END_MONTH", "高温补贴发放结束月份（含）"),
    HOT_ALLOWANCE("HOT_ALLOWANCE", "高温补贴（元/日）"),
    SOCIAL_SECURITY_ALLOWANCE("SOCIAL_SECURITY_ALLOWANCE", "社保补贴（元/日）"),
    HOUSE_FUND_ALLOWANCE("HOUSE_FUND_ALLOWANCE", "住房补贴（元/日）");

    private String code;
    private String value;

    SystemConfigTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

//    @JsonValue
    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}

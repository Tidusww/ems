package com.ly.ems.model.base.employee.constant;


import com.fasterxml.jackson.annotation.JsonValue;
import com.ly.ems.core.mybatis.BaseCodeValueEnum;

public enum SalaryBankEnum implements BaseCodeValueEnum {

    /**
     * 工资银行：
     * 0: 未知
     * 1: 有效
     * 2: 无效
     */
    BOC("BOC", "中国银行"),
    ICBC("ICBC", "中国工商银行"),
    CCB("CCB", "中国建设银行"),
    ABC("ABC", "中国农业银行");

    private String code;
    private String value;

    SalaryBankEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @JsonValue
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public void setCode(String code) {
        this.code = code;
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

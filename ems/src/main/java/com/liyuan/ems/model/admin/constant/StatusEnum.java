package com.liyuan.ems.model.admin.constant;


import com.liyuan.ems.core.mybatis.BaseKeyValueEnum;
import org.codehaus.jackson.annotate.JsonValue;

public enum StatusEnum implements BaseKeyValueEnum {
    /**
     * 状态：
     * wx:微信
     * alipay:支付宝
     * ye:余额支付
     * yewx:余额微信
     * yealipay:余额支付宝
     */
    UNKNOWN(0, "位置"),
    ACTIVED(1, "PC端"),
    DISABLED(2, "APP端");

    private Integer key;
    private String value;

    StatusEnum(Integer key, String value) {
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

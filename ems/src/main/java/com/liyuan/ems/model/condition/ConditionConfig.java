package com.liyuan.ems.model.condition;

import com.liyuan.ems.model.common.constant.StatusEnum;

/**
 * Created by tidus on 2017/9/12.
 */
public class ConditionConfig {

    private String configCode;
    private String conditionCode;
    private int orderBy;
    private StatusEnum status;

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}

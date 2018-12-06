package com.ly.ems.model.condition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ly.ems.model.common.constant.StatusEnum;
import com.ly.ems.model.condition.constants.ConditionDatasource;
import com.ly.ems.model.condition.constants.ConditionType;

/**
 * Created by tidus on 2017/9/13.
 */
public class ConditionItem {

    private String conditionCode;
    private String conditionKey;
    private String conditionName;
    private String conditionPlaceholder;
    private ConditionType conditionType;
    private ConditionDatasource conditionDatasource;
    @JsonIgnore
    private String conditionSql;
    @JsonIgnore
    private String conditionEnum;
    private String conditionParentKey;
    private String conditionExt;
    private StatusEnum status;


    public ConditionDatasource getConditionDatasource() {
        return conditionDatasource;
    }

    public void setConditionDatasource(ConditionDatasource conditionDatasource) {
        this.conditionDatasource = conditionDatasource;
    }

    public String getConditionParentKey() {
        return conditionParentKey;
    }

    public void setConditionParentKey(String conditionParentKey) {
        this.conditionParentKey = conditionParentKey;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getConditionExt() {
        return conditionExt;
    }

    public void setConditionExt(String conditionExt) {
        this.conditionExt = conditionExt;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getConditionKey() {
        return conditionKey;
    }

    public void setConditionKey(String conditionKey) {
        this.conditionKey = conditionKey;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getConditionPlaceholder() {
        return conditionPlaceholder;
    }

    public void setConditionPlaceholder(String conditionPlaceholder) {
        this.conditionPlaceholder = conditionPlaceholder;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionSql() {
        return conditionSql;
    }

    public void setConditionSql(String conditionSql) {
        this.conditionSql = conditionSql;
    }

    public String getConditionEnum() {
        return conditionEnum;
    }

    public void setConditionEnum(String conditionEnum) {
        this.conditionEnum = conditionEnum;
    }
}

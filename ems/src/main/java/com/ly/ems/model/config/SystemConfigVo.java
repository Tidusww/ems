package com.ly.ems.model.config;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by tidus on 2018/10/25.
 */
public class SystemConfigVo extends SystemConfig {
    public String getConfigTypeValue() {
        if(super.getConfigValue() == null) {
            return "";
        }
        return super.getConfigType().getValue();
    }


    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
    public Date getCreateDate() {
        return super.getCreateDate();
    }


    @Override
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
    public Date getUpdateDate() {
        return super.getUpdateDate();
    }

}

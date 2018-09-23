package com.ly.ems.model.base.job;

/**
 * Created by tidus on 2018/9/23.
 */
public class JobVo extends Job {
    public String getIsSpecValue() {
        if(super.getIsSpec() == null) {
            return "";
        }
        return super.getIsSpec().getValue();
    }
}

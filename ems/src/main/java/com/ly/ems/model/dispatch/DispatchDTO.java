package com.ly.ems.model.dispatch;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.ems.core.mybatis.pagehelper.PageableModel;

import java.util.Date;

/**
 * Created by tidus on 2018/7/2.
 */
public class DispatchDTO extends Dispatch {

    @Override
    @JsonFormat(pattern = "yyyy-MM", locale = "zh" , timezone="GMT+8")
    public Date getStartDate() {
        return super.getStartDate();
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM", locale = "zh" , timezone="GMT+8")
    public Date getEndDate() {
        return super.getEndDate();
    }

}

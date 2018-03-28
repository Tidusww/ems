package com.ly.ems.model.base.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.ems.model.common.constant.StatusEnum;

import java.util.Date;

/**
 * Created by tidus on 2018/2/23.
 */
public class ProjectDTO extends Project {

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
    public Date getStartDate() {
        return super.getStartDate();
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
    public Date getEndDate() {
        return super.getEndDate();
    }

}

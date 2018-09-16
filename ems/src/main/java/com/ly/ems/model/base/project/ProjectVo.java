package com.ly.ems.model.base.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

/**
 * Created by tidus on 2018/2/23.
 */
public class ProjectVo extends Project {

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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

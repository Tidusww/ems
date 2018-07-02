package com.ly.ems.model.base.company;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;

/**
 * Created by tidus on 2017/11/18.
 */
public class Company extends PageableModel {

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}

package com.ly.ems.model.base.company;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;

/**
 * Created by tidus on 2017/10/31.
 */
public class CompanyConditions extends PageableModel {

    /** 公司名称*/
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}

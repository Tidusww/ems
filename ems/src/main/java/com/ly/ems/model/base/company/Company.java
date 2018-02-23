package com.ly.ems.model.base.company;

import com.ly.ems.model.BaseModel;
import com.ly.ems.model.common.constant.StatusEnum;

/**
 * Created by tidus on 2017/11/18.
 */
public class Company extends BaseModel{

    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}

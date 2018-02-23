package com.ly.ems.model.base.area;

import com.ly.ems.core.mybatis.pagehelper.PageableCondition;
import com.ly.ems.model.common.constant.YesNoEnum;
import com.ly.ems.core.mybatis.pagehelper.PageableCondition;

/**
 * Created by tidus on 2017/10/31.
 */
public class AreaConditions extends PageableCondition {

    /** 地区名称*/
    private String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

}

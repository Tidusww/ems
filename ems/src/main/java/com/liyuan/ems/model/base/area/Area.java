package com.liyuan.ems.model.base.area;

import com.liyuan.ems.model.common.constant.StatusEnum;

/**
 * Created by tidus on 2017/11/18.
 */
public class Area {


    private Integer id;
    private String areaName;
    private StatusEnum status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}

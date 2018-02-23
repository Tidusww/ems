package com.ly.ems.model;

import com.ly.ems.model.common.constant.StatusEnum;

/**
 * Created by tidus on 2018/2/23.
 */
public class BaseModel {

    private Integer id;
    private StatusEnum status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }


    /**
     * 状态文本
     * @return
     */
    public String getStatusValue() {
        if(this.getStatus() == null){
            return "";
        }
        return this.getStatus().getValue();
    }
}

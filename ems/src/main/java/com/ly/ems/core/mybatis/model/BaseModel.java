package com.ly.ems.core.mybatis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.ems.model.common.constant.StatusEnum;

import java.util.Date;

/**
 * Created by tidus on 2018/2/23.
 * 抽出id 和 status createDate updateDate
 */
public abstract class BaseModel {

    private Integer id;
    private StatusEnum status;
    private Date createDate;
    private Date updateDate;

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

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

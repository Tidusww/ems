package com.ly.ems.core.mybatis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.common.constant.StatusEnum;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by tidus on 2018/2/23.
 * 抽出id 和 enable createDate updateDate
 */
@Deprecated
public abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 状态
     */
    @Column(name = "enable")
    private EnableEnum enable;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 更新人id
     */
    @Column(name = "update_user_id")
    private Integer updateUserId;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public EnableEnum getEnable() {
        return enable;
    }
    public void setEnable(EnableEnum enable) {
        this.enable = enable;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
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
    public String getEnableValue() {
        if(this.getEnable() == null){
            return "";
        }
        return this.getEnable().getValue();
    }

}

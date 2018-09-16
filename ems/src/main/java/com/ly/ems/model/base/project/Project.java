package com.ly.ems.model.base.project;

import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.common.constant.StatusEnum;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`ly_project`")
public class Project {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 所属单位
     */
    @Column(name = "`company_id`")
    private Integer companyId;

    /**
     * 项目名称
     */
    @Column(name = "`project_name`")
    private String projectName;

    /**
     * 项目描述
     */
    @Column(name = "`project_desc`")
    private String projectDesc;

    /**
     * 开工日期：2018-01-01
     */
    @Column(name = "`start_date`")
    private Date startDate;

    /**
     * 完工日期：2018-02-01
     */
    @Column(name = "`end_date`")
    private Date endDate;

    /**
     * 状态, 0：未知，1：有效， 2：无效
     */
    @Column(name = "`status`")
    private StatusEnum status;

    /**
     * 启用状态, 0：禁用，1：启用
     */
    @Column(name = "`enable`")
    private EnableEnum enable;

    /**
     * 创建时间
     */
    @Column(name = "`create_date`")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @Column(name = "`update_date`")
    private Date updateDate;

    /**
     * 更新人id
     */
    @Column(name = "`update_user_id`")
    private Integer updateUserId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取所属单位
     *
     * @return company_id - 所属单位
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 设置所属单位
     *
     * @param companyId 所属单位
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取项目名称
     *
     * @return project_name - 项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * 设置项目名称
     *
     * @param projectName 项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * 获取项目描述
     *
     * @return project_desc - 项目描述
     */
    public String getProjectDesc() {
        return projectDesc;
    }

    /**
     * 设置项目描述
     *
     * @param projectDesc 项目描述
     */
    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    /**
     * 获取开工日期：2018-01-01
     *
     * @return start_date - 开工日期：2018-01-01
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置开工日期：2018-01-01
     *
     * @param startDate 开工日期：2018-01-01
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取完工日期：2018-02-01
     *
     * @return end_date - 完工日期：2018-02-01
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置完工日期：2018-02-01
     *
     * @param endDate 完工日期：2018-02-01
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取状态, 0：未知，1：有效， 2：无效
     *
     * @return status - 状态, 0：未知，1：有效， 2：无效
     */
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * 设置状态, 0：未知，1：有效， 2：无效
     *
     * @param status 状态, 0：未知，1：有效， 2：无效
     */
    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    /**
     * 获取启用状态, 0：禁用，1：启用
     *
     * @return enable - 启用状态, 0：禁用，1：启用
     */
    public EnableEnum getEnable() {
        return enable;
    }

    /**
     * 设置启用状态, 0：禁用，1：启用
     *
     * @param enable 启用状态, 0：禁用，1：启用
     */
    public void setEnable(EnableEnum enable) {
        this.enable = enable;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取最后更新时间
     *
     * @return update_date - 最后更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置最后更新时间
     *
     * @param updateDate 最后更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取更新人id
     *
     * @return update_user_id - 更新人id
     */
    public Integer getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人id
     *
     * @param updateUserId 更新人id
     */
    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}
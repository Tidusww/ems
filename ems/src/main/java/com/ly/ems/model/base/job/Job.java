package com.ly.ems.model.base.job;

import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.common.constant.StatusEnum;
import com.ly.ems.model.common.constant.YesNoEnum;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`ly_job`")
public class Job {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 工种名称
     */
    @Column(name = "`job_name`")
    private String jobName;

    /**
     * 是否特殊工种，，0：否 1：是
     */
    @Column(name = "`is_spec`")
    private YesNoEnum isSpec;

    /**
     * 工种工资
     */
    @Column(name = "`salary`")
    private BigDecimal salary;

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
     * 获取工种名称
     *
     * @return job_name - 工种名称
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * 设置工种名称
     *
     * @param jobName 工种名称
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * 获取是否特殊工种，，0：否 1：是
     *
     * @return is_spec - 是否特殊工种，，0：否 1：是
     */
    public YesNoEnum getIsSpec() {
        return isSpec;
    }

    /**
     * 设置是否特殊工种，，0：否 1：是
     *
     * @param isSpec 是否特殊工种，，0：否 1：是
     */
    public void setIsSpec(YesNoEnum isSpec) {
        this.isSpec = isSpec;
    }

    /**
     * 获取工种工资
     *
     * @return salary - 工种工资
     */
    public BigDecimal getSalary() {
        return salary;
    }

    /**
     * 设置工种工资
     *
     * @param salary 工种工资
     */
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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
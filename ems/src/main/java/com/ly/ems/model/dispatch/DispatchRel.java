package com.ly.ems.model.dispatch;

import com.ly.ems.model.common.constant.EnableEnum;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`ly_dispatch_rel`")
public class DispatchRel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 班组id
     */
    @Column(name = "`group_id`")
    private Integer groupId;

    /**
     * 项目id
     */
    @Column(name = "`project_id`")
    private Integer projectId;

    /**
     * 派遣开始月份：2018-01
     */
    @Column(name = "`start_date`")
    private Date startDate;

    /**
     * 派遣结束月份：2018-02
     */
    @Column(name = "`end_date`")
    private Date endDate;

    /**
     * 状态, 0：无效，1：有效
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
     * 获取班组id
     *
     * @return group_id - 班组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置班组id
     *
     * @param groupId 班组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取项目id
     *
     * @return project_id - 项目id
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 设置项目id
     *
     * @param projectId 项目id
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 获取派遣开始月份：2018-01
     *
     * @return start_date - 派遣开始月份：2018-01
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置派遣开始月份：2018-01
     *
     * @param startDate 派遣开始月份：2018-01
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取派遣结束月份：2018-02
     *
     * @return end_date - 派遣结束月份：2018-02
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置派遣结束月份：2018-02
     *
     * @param endDate 派遣结束月份：2018-02
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取状态, 0：无效，1：有效
     *
     * @return enable - 状态, 0：无效，1：有效
     */
    public EnableEnum getEnable() {
        return enable;
    }

    /**
     * 设置状态, 0：无效，1：有效
     *
     * @param enable 状态, 0：无效，1：有效
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
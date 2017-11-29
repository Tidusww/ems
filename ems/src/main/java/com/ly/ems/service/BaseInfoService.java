package com.ly.ems.service;

import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.area.AreaConditions;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.model.base.job.Job;

/**
 * 基础信息
 * Created by tidus on 2017/11/19.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface BaseInfoService {


    /**
     * **************** 班组 ****************
     * @param conditions
     * @return
     */
    PageableResult<Group> getGroupsByConditions(GroupConditions conditions);
    void saveGroup(Group group);
    void disableGroup(Integer id);
    void deleteGroup(Integer id);

    /**
     * **************** 地区 ****************
     * @param conditions
     * @return
     */
    PageableResult<Area> getAreasByConditions(AreaConditions conditions);
    void saveArea(Area job);
    void disableArea(Integer id);
    void deleteArea(Integer id);

    /**
     * **************** 工种 ****************
     * @param conditions
     * @return
     */
    PageableResult<Job> getJobsByConditions(JobConditions conditions);
    void saveJob(Job job);
    void disableJob(Integer id);
    void deleteJob(Integer id);
}

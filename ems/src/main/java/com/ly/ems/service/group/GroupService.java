package com.ly.ems.service.group;

import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.group.GroupVo;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectVo;
import com.ly.ems.model.common.PageableResult;


/**
 * 基础信息
 * Created by tidus on 2017/11/19.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface GroupService {


    /**
     * **************** 2.班组 ****************
     * @param conditions
     * @return
     */
    PageableResult<GroupVo> getGroupsByConditions(GroupConditions conditions);
    Group selectOneGroup(Group group);
    void saveGroup(Group group, Integer projectId);
    void disableGroup(Integer id);
    void deleteGroup(Integer id);

}

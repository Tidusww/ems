package com.ly.ems.service.project;

import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectVo;
import com.ly.ems.model.common.PageableResult;


/**
 * 基础信息
 * Created by tidus on 2017/11/19.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface ProjectService {

    /**
     * **************** 5.项目 ****************
     * @param conditions
     * @return
     */
    PageableResult<ProjectVo> getProjectsByConditions(ProjectConditions conditions);
    void saveProject(Project job);
    void disableProject(Integer id);
    void deleteProject(Integer id);
}

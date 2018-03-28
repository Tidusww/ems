package com.ly.ems.dao.base;

import com.ly.ems.dao.BaseMapper;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectDTO;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface ProjectMapper extends BaseMapper<Project, ProjectConditions> {


    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    List<ProjectDTO> getByConditions(ProjectConditions conditions);

}

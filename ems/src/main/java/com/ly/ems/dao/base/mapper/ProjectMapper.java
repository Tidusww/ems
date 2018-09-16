package com.ly.ems.dao.base.mapper;

import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectExample;
import java.util.List;
import tk.mybatis.mapper.common.BaseMapper;

public interface ProjectMapper extends BaseMapper<Project> {
    List<Project> selectByExample(ProjectExample example);
}
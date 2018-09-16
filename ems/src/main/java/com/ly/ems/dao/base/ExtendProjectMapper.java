package com.ly.ems.dao.base;

import com.ly.ems.core.mybatis.dao.ConditionMapper;
import com.ly.ems.dao.base.mapper.ProjectMapper;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectVo;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface ExtendProjectMapper extends ConditionMapper<ProjectVo, ProjectConditions> {

}

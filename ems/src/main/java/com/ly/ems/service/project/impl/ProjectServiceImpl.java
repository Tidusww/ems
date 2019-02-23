package com.ly.ems.service.project.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.dao.base.*;
import com.ly.ems.dao.base.mapper.*;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectVo;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.service.project.ProjectService;
import com.ly.ems.service.dispatch.DispatchService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    DispatchService dispatchService;

    @Autowired
    ExtendEmployeeMapper extendEmployeeMapper;
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    ExtendGroupMapper extendGroupMapper;
    @Autowired
    GroupMapper groupMapper;

    @Autowired
    ExtendJobMapper extendJobMapper;
    @Autowired
    JobMapper jobMapper;

    @Autowired
    ExtendCompanyMapper extendCompanyMapper;
    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    ExtendProjectMapper extendProjectMapper;
    @Autowired
    ProjectMapper projectMapper;


    /**
     * **************** 项目 ****************
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<ProjectVo> getProjectsByConditions(ProjectConditions conditions) {

        List<ProjectVo> resultList = extendProjectMapper.selectByConditions(conditions);
        PageInfo<ProjectVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<ProjectVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void saveProject(Project project) {
        if (project.getId() == null) {
            project.setEnable(EnableEnum.ENABLED);
            projectMapper.insertSelective(project);
        } else {
            projectMapper.updateByPrimaryKeySelective(project);
        }
    }

    @Override
    public void disableProject(Integer id) {
        Project project = new Project();
        project.setId(id);
        project.setEnable(EnableEnum.DISABLED);
        projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    public void deleteProject(Integer id) {
        projectMapper.deleteByPrimaryKey(id);
    }

    private String currentUser() {

        Subject currentUser = SecurityUtils.getSubject();
        return "";
    }
}
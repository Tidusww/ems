package com.ly.ems.service.dispatch.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.dao.base.ExtendEmployeeMapper;
import com.ly.ems.dao.base.mapper.ProjectMapper;
import com.ly.ems.dao.dispatch.DispatchRelMapper;
import com.ly.ems.dao.dispatch.ExtendDispatchRelMapper;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeVo;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.dispatch.DispatchRel;
import com.ly.ems.model.dispatch.DispatchRelCondition;
import com.ly.ems.model.dispatch.DispatchRelVo;
import com.ly.ems.service.dispatch.DispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tidus on 2018/1/15.
 */
@Service
public class DispatchServiceImpl implements DispatchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatchServiceImpl.class);



    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    DispatchRelMapper dispatchRelMapper;

    @Autowired
    ExtendDispatchRelMapper extendDispatchRelMapper;

    @Autowired
    ExtendEmployeeMapper extendEmployeeMapper;

    /**
     * 查询派遣关系
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<DispatchRelVo> getDispatchRelsByConditions(DispatchRelCondition conditions) {
        List<DispatchRelVo> resultList = extendDispatchRelMapper.selectByConditions(conditions);
        PageInfo<DispatchRelVo> pageInfo = new PageInfo(resultList);
        return new PageableResult<DispatchRelVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);
    }

    /**
     * 派遣班组到项目中，将现有派遣关系置为关系无效，再插入新
     * @param group
     * @param projectId
     */
    @Override
    public void dispatchGroupToProjectByProjectId(Group group, Integer projectId) {
        DispatchRel dispatchRel = new DispatchRel();
        dispatchRel.setGroupId(group.getId());
        dispatchRel.setEnable(EnableEnum.ENABLED);
        List<DispatchRel> groupDispatchRels = dispatchRelMapper.select(dispatchRel);

        // 置为无效
        for(DispatchRel groupDispatchRel : groupDispatchRels) {
            DispatchRel updateDispatchRel = new DispatchRel();
            updateDispatchRel.setId(groupDispatchRel.getId());
            updateDispatchRel.setEnable(EnableEnum.DISABLED);
            dispatchRelMapper.updateByPrimaryKeySelective(updateDispatchRel);
        }

        // 插入新关系
        Project project =  projectMapper.selectByPrimaryKey(projectId);
        DispatchRel insertDispatchRel = new DispatchRel();
        insertDispatchRel.setGroupId(group.getId());
        insertDispatchRel.setProjectId(projectId);
        insertDispatchRel.setStartDate(project.getStartDate());
        insertDispatchRel.setEndDate(project.getEndDate());
        insertDispatchRel.setEnable(EnableEnum.ENABLED);
        dispatchRelMapper.insertSelective(insertDispatchRel);

    }


}

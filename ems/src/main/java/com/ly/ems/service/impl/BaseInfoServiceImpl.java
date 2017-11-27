package com.ly.ems.service.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.dao.admin.AdminMapper;
import com.ly.ems.dao.admin.MenuMapper;
import com.ly.ems.dao.base.AreaMapper;
import com.ly.ems.dao.base.GroupMapper;
import com.ly.ems.dao.base.JobMapper;
import com.ly.ems.model.admin.Menu;
import com.ly.ems.model.admin.Permission;
import com.ly.ems.model.admin.Role;
import com.ly.ems.model.admin.User;
import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.area.AreaConditions;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.StatusEnum;
import com.ly.ems.service.AdminService;
import com.ly.ems.service.BaseInfoService;
import com.ly.ems.dao.base.AreaMapper;
import com.ly.ems.dao.base.JobMapper;
import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.service.BaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BaseInfoServiceImpl implements BaseInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInfoServiceImpl.class);

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    AreaMapper areaMapper;

    @Autowired
    JobMapper jobMapper;


    @Override
    public PageableResult<Group> getGroupsByConditions(GroupConditions conditions) {

        List<Group> resultList = groupMapper.getGroupsByConditions(conditions);
        PageInfo<Group> pageInfo = new PageInfo(resultList);

        return new PageableResult<Group>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public PageableResult<Area> getAreasByConditions(AreaConditions conditions) {

        List<Area> resultList = areaMapper.getAreasByConditions(conditions);
        PageInfo<Area> pageInfo = new PageInfo(resultList);

        return new PageableResult<Area>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public PageableResult<Job> getJobsByConditions(JobConditions conditions) {

        List<Job> resultList = jobMapper.getJobsByConditions(conditions);
        PageInfo<Job> pageInfo = new PageInfo(resultList);

        return new PageableResult<Job>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }
    @Override
    public void saveJob(Job job) {
        if(job.getId() == null){
            job.setStatus(StatusEnum.ACTIVED);
            jobMapper.insertJob(job);
        }else{
            jobMapper.updateJob(job);
        }
    }
    @Override
    public void disableJob(Integer id) {
        jobMapper.updateJobStatus(id, StatusEnum.DISABLED);
    }
    @Override
    public void deleteJob(Integer id) {
        jobMapper.deleteJob(id);
    }
}
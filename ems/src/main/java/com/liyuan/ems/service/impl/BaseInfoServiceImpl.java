package com.liyuan.ems.service.impl;

import com.github.pagehelper.PageInfo;
import com.liyuan.ems.dao.admin.AdminMapper;
import com.liyuan.ems.dao.admin.MenuMapper;
import com.liyuan.ems.dao.base.AreaMapper;
import com.liyuan.ems.dao.base.GroupMapper;
import com.liyuan.ems.dao.base.JobMapper;
import com.liyuan.ems.model.admin.Menu;
import com.liyuan.ems.model.admin.Permission;
import com.liyuan.ems.model.admin.Role;
import com.liyuan.ems.model.admin.User;
import com.liyuan.ems.model.base.area.Area;
import com.liyuan.ems.model.base.area.AreaConditions;
import com.liyuan.ems.model.base.group.Group;
import com.liyuan.ems.model.base.group.GroupConditions;
import com.liyuan.ems.model.base.job.Job;
import com.liyuan.ems.model.base.job.JobConditions;
import com.liyuan.ems.model.common.PageableResult;
import com.liyuan.ems.model.common.constant.StatusEnum;
import com.liyuan.ems.service.AdminService;
import com.liyuan.ems.service.BaseInfoService;
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
}
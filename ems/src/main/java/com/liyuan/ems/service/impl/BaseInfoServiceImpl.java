package com.liyuan.ems.service.impl;

import com.github.pagehelper.PageInfo;
import com.liyuan.ems.dao.admin.AdminMapper;
import com.liyuan.ems.dao.admin.MenuMapper;
import com.liyuan.ems.dao.base.GroupMapper;
import com.liyuan.ems.model.admin.Menu;
import com.liyuan.ems.model.admin.Permission;
import com.liyuan.ems.model.admin.Role;
import com.liyuan.ems.model.admin.User;
import com.liyuan.ems.model.base.group.Group;
import com.liyuan.ems.model.base.group.GroupConditions;
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


    @Override
    public PageableResult<Group> getGroupsByConditions(GroupConditions conditions) {

        List<Group> groupList = groupMapper.getGroupsByCondition(conditions);
        PageInfo<Group> pageInfo = new PageInfo(groupList);

        return new PageableResult<Group>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), groupList);

    }
}
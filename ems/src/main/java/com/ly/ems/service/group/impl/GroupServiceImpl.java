package com.ly.ems.service.group.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.dao.base.ExtendGroupMapper;
import com.ly.ems.dao.base.mapper.GroupMapper;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.group.GroupVo;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.service.dispatch.DispatchService;
import com.ly.ems.service.group.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupServiceImpl implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    DispatchService dispatchService;

    @Autowired
    ExtendGroupMapper extendGroupMapper;
    @Autowired
    GroupMapper groupMapper;




    /**
     * 班组
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<GroupVo> getGroupsByConditions(GroupConditions conditions) {

        List<GroupVo> resultList = extendGroupMapper.selectByConditions(conditions);
        PageInfo<GroupVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<GroupVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public Group selectOneGroup(Group group) {
        return groupMapper.selectOne(group);
    }

    @Override
    public void saveGroup(Group group, Integer projectId) {
        if (group.getId() == null) {
            group.setEnable(EnableEnum.ENABLED);
            groupMapper.insertSelective(group);
        } else {
            groupMapper.updateByPrimaryKeySelective(group);
        }

        if (projectId != null) {
            dispatchService.dispatchGroupToProjectByProjectId(group, projectId);
        }
    }

    @Override
    public void disableGroup(Integer id) {
        Group group = new Group();
        group.setId(id);
        group.setEnable(EnableEnum.DISABLED);
        groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public void deleteGroup(Integer id) {
        groupMapper.deleteByPrimaryKey(id);
    }


}
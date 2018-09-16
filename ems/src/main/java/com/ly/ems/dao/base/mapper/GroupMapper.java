package com.ly.ems.dao.base.mapper;

import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupExample;
import java.util.List;
import tk.mybatis.mapper.common.BaseMapper;

public interface GroupMapper extends BaseMapper<Group> {
    List<Group> selectByExample(GroupExample example);
}
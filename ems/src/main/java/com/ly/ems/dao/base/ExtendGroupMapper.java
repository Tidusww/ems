package com.ly.ems.dao.base;

import com.ly.ems.core.mybatis.dao.ConditionMapper;
import com.ly.ems.dao.base.mapper.GroupMapper;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupVo;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface ExtendGroupMapper extends ConditionMapper<GroupVo, GroupConditions> {

}

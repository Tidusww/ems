package com.liyuan.ems.service;

import com.liyuan.ems.core.datasource.DetermineDataSource;
import com.liyuan.ems.core.datasource.MultipleRoutingDataSource;
import com.liyuan.ems.model.base.group.Group;
import com.liyuan.ems.model.base.group.GroupConditions;
import com.liyuan.ems.model.common.PageableResult;

/**
 * 基础信息
 * Created by tidus on 2017/11/19.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface BaseInfoService {


    /**
     * **************** 班组 ****************
     */
    PageableResult<Group> getGroupsByConditions(GroupConditions conditions);


}

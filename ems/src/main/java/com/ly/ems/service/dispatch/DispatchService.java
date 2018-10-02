package com.ly.ems.service.dispatch;
import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.dispatch.DispatchRelCondition;
import com.ly.ems.model.dispatch.DispatchRelVo;

/**
 * Created by tidus on 2017/9/13.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface DispatchService {
    /**
     * 查询派遣关系
     * @param conditions
     * @return
     */
    PageableResult<DispatchRelVo> getDispatchRelsByConditions(DispatchRelCondition conditions);


    /**
     * 派遣班组到项目中，将现有派遣关系置为关系无效，再插入新的派遣关系
     * @param group
     * @param projectId
     */
    void dispatchGroupToProjectByProjectId(Group group, Integer projectId);
}

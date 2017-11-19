package com.liyuan.ems.dao.base;

import com.liyuan.ems.model.base.group.Group;
import com.liyuan.ems.model.base.group.GroupConditions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface GroupMapper {

    /**
     * 根据id查找job
     *
     * @param id
     * @return
     */
    Group getGroupById(@Param("id") Integer id);

    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    List<Group> getGroupsByCondition(GroupConditions conditions);
}

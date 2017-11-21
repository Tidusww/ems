package com.ly.ems.dao.base;

import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.group.GroupConditions;
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
    List<Group> getGroupsByConditions(GroupConditions conditions);
}

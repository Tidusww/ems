package com.ly.ems.dao.base;

import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.common.constant.StatusEnum;
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


    /**
     * 新增Group
     * @param group
     */
    void insertGroup(Group group);

    /**
     * 修改Group
     * @param group
     */
    void updateGroup(Group group);

    /**
     * 修改Group状态
     * @param id
     */
    void updateGroupStatus(@Param("id")Integer id, @Param("status")StatusEnum statusEnum);

    /**
     * 删除Group
     * @param id
     */
    void deleteGroup(@Param("id")Integer id);
}

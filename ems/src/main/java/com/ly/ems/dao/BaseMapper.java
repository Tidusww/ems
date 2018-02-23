package com.ly.ems.dao;

import com.ly.ems.core.mybatis.pagehelper.PageableCondition;
import com.ly.ems.model.BaseModel;
import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.area.AreaConditions;
import com.ly.ems.model.common.constant.StatusEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface BaseMapper {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    Area getById(@Param("id") Integer id);

    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    List<Area> getByConditions(PageableCondition conditions);

    /**
     * 新增
     * @param
     */
    void insert(BaseModel model);

    /**
     * 修改
     * @param
     */
    void update(BaseModel model);

    /**
     * 修改 状态
     * @param id
     */
    void updateStatus(@Param("id") Integer id, @Param("status") StatusEnum statusEnum);

    /**
     * 删除
     * @param id
     */
    void delete(@Param("id") Integer id);

}

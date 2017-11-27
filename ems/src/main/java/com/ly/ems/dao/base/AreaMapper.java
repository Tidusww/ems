package com.ly.ems.dao.base;

import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.area.AreaConditions;
import com.ly.ems.model.common.constant.StatusEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface AreaMapper {

    /**
     * 根据id查找job
     *
     * @param id
     * @return
     */
    Area getAreaById(@Param("id") Integer id);

    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    List<Area> getAreasByConditions(AreaConditions conditions);

    /**
     * 新增Area
     * @param area
     */
    void insertArea(Area area);

    /**
     * 修改Area
     * @param area
     */
    void updateArea(Area area);

    /**
     * 修改Area状态
     * @param id
     */
    void updateAreaStatus(@Param("id")Integer id, @Param("status")StatusEnum statusEnum);

    /**
     * 删除Area
     * @param id
     */
    void deleteArea(@Param("id")Integer id);

}

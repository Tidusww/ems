package com.liyuan.ems.dao.base;

import com.liyuan.ems.model.base.area.Area;
import com.liyuan.ems.model.base.area.AreaConditions;
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
}

package com.ly.ems.core.mybatis.dao;

import com.ly.ems.core.mybatis.model.BaseModel;
import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.common.constant.StatusEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2018/3/12.
 */
public interface ConditionMapper<Model, Condition> {


    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    List<Model> selectByConditions(Condition conditions);

//    /**
//     * 根据id查找
//     *
//     * @param id
//     * @return
//     */
//    Model getById(@Param("id") Integer id);
//    /**
//     * 新增
//     * @param
//     */
//    void insert(Model model);
//    /**
//     * 修改
//     * @param
//     */
//    void update(Model model);
//    /**
//     * 修改 状态
//     * @param id
//     */
//    void updateEnable(@Param("id") Integer id, @Param("enable") EnableEnum enableEnum);
//    /**
//     * 删除
//     * @param id
//     */
//    void delete(@Param("id") Integer id);


}

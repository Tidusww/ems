package com.ly.ems.dao;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.model.BaseModel;
import com.ly.ems.model.common.constant.StatusEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface BaseMapper<Model extends BaseModel, Condition extends PageableModel> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    Model getById(@Param("id") Integer id);

    /**
     * 新增
     * @param
     */
    void insert(Model model);

    /**
     * 修改
     * @param
     */
    void update(Model model);

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

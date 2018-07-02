package com.ly.ems.core.mybatis.dao.impl;

import com.ly.ems.core.mybatis.model.BaseModel;
import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.model.common.constant.StatusEnum;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2018/3/12.
 */
@Deprecated
public abstract class AbstractBaseDao<Model extends BaseModel, Condition extends PageableModel> extends SqlSessionDaoSupport /*implements BaseDao<Model, Condition>*/ {

    /* 必须: 使用spring中配置的SessionFactory */
    @Override
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    public Model getById(Integer id) {
        Map<String, Object> param = new HashMap();
        param.put("id", id);
        param.put("table", this.getTableName());
        return (Model)this.getSqlSession().selectList("com.ly.ems.core.mybatis.base.BaseMapper.getById", param);
    }
    /**
     * 修改 状态
     * @param id
     */
    public void updateStatus(@Param("id") Integer id, @Param("status") StatusEnum statusEnum) {

    }
    /**
     * 删除
     * @param id
     */
    public void delete(@Param("id") Integer id) {

    }


    /**
     * =================== 抽象方法 ===================
     */
    /**
     * 表名
     * @return
     */
    protected abstract String getTableName();
    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    public abstract List<Model> getByConditions(Condition conditions);
    /**
     * 新增
     * @param
     */
    public abstract void insert(Model model);
    /**
     * 修改
     * @param
     */
    public abstract void update(Model model);



}

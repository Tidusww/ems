package com.ly.ems.dao.base.impl;

import com.ly.ems.core.mybatis.dao.impl.AbstractBaseDao;
import com.ly.ems.dao.base.CompanyMapper;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;

import java.util.List;

/**
 * Created by tidus on 2018/3/12.
 */
//@Repository
public class CompanyDaoImpl extends AbstractBaseDao<Company, CompanyConditions> implements CompanyMapper{

    /**
     * 表名
     * @return
     */
    @Override
    protected String getTableName() {
        return "ly_company";
    }
    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    @Override
    public List<Company> getByConditions(CompanyConditions conditions) {
        return null;
    }
    /**
     * 新增
     * @param
     */
    @Override
    public void insert(Company model) {

    }
    /**
     * 修改
     * @param
     */
    @Override
    public void update(Company model) {

    }


}

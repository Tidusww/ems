package com.ly.ems.dao.base;

import com.ly.ems.dao.BaseMapper;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface CompanyMapper extends BaseMapper<Company, CompanyConditions> {


    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    List<Company> getByConditions(CompanyConditions conditions);

}

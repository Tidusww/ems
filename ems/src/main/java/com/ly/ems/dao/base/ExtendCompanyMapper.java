package com.ly.ems.dao.base;

import com.ly.ems.core.mybatis.dao.ConditionMapper;
import com.ly.ems.dao.base.mapper.CompanyMapper;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;

/**
 * Created by tidus on 2017/11/19.
 */
public interface ExtendCompanyMapper extends ConditionMapper<Company, CompanyConditions> {


}

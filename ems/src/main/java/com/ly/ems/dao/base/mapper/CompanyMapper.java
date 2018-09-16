package com.ly.ems.dao.base.mapper;

import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyExample;
import java.util.List;
import tk.mybatis.mapper.common.BaseMapper;

public interface CompanyMapper extends BaseMapper<Company> {
    List<Company> selectByExample(CompanyExample example);
}
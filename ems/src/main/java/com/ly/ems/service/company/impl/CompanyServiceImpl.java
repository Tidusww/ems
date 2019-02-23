package com.ly.ems.service.company.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.dao.base.ExtendCompanyMapper;
import com.ly.ems.dao.base.mapper.CompanyMapper;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.service.company.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);


    @Autowired
    ExtendCompanyMapper extendCompanyMapper;
    @Autowired
    CompanyMapper companyMapper;




    /**
     * **************** 单位 ****************
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<Company> getCompaniesByConditions(CompanyConditions conditions) {

        List<Company> resultList = extendCompanyMapper.selectByConditions(conditions);
        PageInfo<Company> pageInfo = new PageInfo(resultList);

        return new PageableResult<Company>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void saveCompany(Company company) {
        if (company.getId() == null) {
            company.setEnable(EnableEnum.ENABLED);
            companyMapper.insertSelective(company);
        } else {
            companyMapper.updateByPrimaryKeySelective(company);
        }
    }

    @Override
    public void disableCompany(Integer id) {
        Company company = new Company();
        company.setId(id);
        company.setEnable(EnableEnum.DISABLED);
        companyMapper.updateByPrimaryKeySelective(company);
    }

    @Override
    public void deleteCompany(Integer id) {
        companyMapper.deleteByPrimaryKey(id);
    }

}
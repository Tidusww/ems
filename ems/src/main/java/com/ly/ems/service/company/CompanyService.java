package com.ly.ems.service.company;

import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectVo;
import com.ly.ems.model.common.PageableResult;


/**
 * 基础信息
 * Created by tidus on 2017/11/19.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface CompanyService {



    /**
     * **************** 4.单位 ****************
     * @param conditions
     * @return
     */
    PageableResult<Company> getCompaniesByConditions(CompanyConditions conditions);
    void saveCompany(Company job);
    void disableCompany(Integer id);
    void deleteCompany(Integer id);

}

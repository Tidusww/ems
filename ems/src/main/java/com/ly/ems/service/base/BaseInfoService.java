package com.ly.ems.service.base;

import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeVo;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.group.GroupVo;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectVo;
import com.ly.ems.model.common.PageableResult;
/**
 * 基础信息
 * Created by tidus on 2017/11/19.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface BaseInfoService {
    /**
     * **************** 1.员工 ****************
     * @param conditions
     * @return
     */
    PageableResult<EmployeeVo> getEmployeesByConditions(EmployeeConditions conditions);
    void saveEmployee(Employee employee);
    void disableEmployee(Integer id);
    void deleteEmployee(Integer id);

    /**
     * **************** 2.班组 ****************
     * @param conditions
     * @return
     */
    PageableResult<GroupVo> getGroupsByConditions(GroupConditions conditions);
    void saveGroup(Group group, Integer projectId);
    void disableGroup(Integer id);
    void deleteGroup(Integer id);


    /**
     * **************** 3.工种 ****************
     * @param conditions
     * @return
     */
    PageableResult<Job> getJobsByConditions(JobConditions conditions);
    void saveJob(Job job);
    void disableJob(Integer id);
    void deleteJob(Integer id);

    /**
     * **************** 4.单位 ****************
     * @param conditions
     * @return
     */
    PageableResult<Company> getCompaniesByConditions(CompanyConditions conditions);
    void saveCompany(Company job);
    void disableCompany(Integer id);
    void deleteCompany(Integer id);

    /**
     * **************** 5.项目 ****************
     * @param conditions
     * @return
     */
    PageableResult<ProjectVo> getProjectsByConditions(ProjectConditions conditions);
    void saveProject(Project job);
    void disableProject(Integer id);
    void deleteProject(Integer id);
}

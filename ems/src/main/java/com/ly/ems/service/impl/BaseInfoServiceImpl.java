package com.ly.ems.service.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.dao.base.*;
import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.area.AreaConditions;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeDTO;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectDTO;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.StatusEnum;
import com.ly.ems.service.BaseInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BaseInfoServiceImpl implements BaseInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInfoServiceImpl.class);

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    AreaMapper areaMapper;

    @Autowired
    JobMapper jobMapper;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    ProjectMapper projectMapper;

    /**
     * 员工
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<EmployeeDTO> getEmployeesByConditions(EmployeeConditions conditions) {

        List<EmployeeDTO> resultList = employeeMapper.getEmployeesByConditions(conditions);
        PageInfo<EmployeeDTO> pageInfo = new PageInfo(resultList);

        return new PageableResult<EmployeeDTO>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);
    }

    @Override
    public void saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            if (employee.getGroupId() != null) {
                Group group = groupMapper.getGroupById(employee.getGroupId());
                employee.setGroupName(group.getGroupName());
            }
            if (employee.getJobId() != null) {
                Job job = jobMapper.getJobById(employee.getJobId());
                employee.setJobName(job.getJobName());
            }
            employee.setStatus(StatusEnum.ACTIVED);
            employeeMapper.insertEmployee(employee);
        } else {
            employeeMapper.updateEmployee(employee);
        }
    }

    @Override
    public void disableEmployee(Integer id) {
        employeeMapper.updateEmployeeStatus(id, StatusEnum.DISABLED);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeMapper.deleteEmployee(id);
    }


    /**
     * 班组
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<Group> getGroupsByConditions(GroupConditions conditions) {

        List<Group> resultList = groupMapper.getGroupsByConditions(conditions);
        PageInfo<Group> pageInfo = new PageInfo(resultList);

        return new PageableResult<Group>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void saveGroup(Group group) {
        if (group.getId() == null) {
            group.setStatus(StatusEnum.ACTIVED);
            groupMapper.insertGroup(group);
        } else {
            groupMapper.updateGroup(group);
        }
    }

    @Override
    public void disableGroup(Integer id) {
        groupMapper.updateGroupStatus(id, StatusEnum.DISABLED);
    }

    @Override
    public void deleteGroup(Integer id) {
        groupMapper.deleteGroup(id);
    }


    /**
     * 地区
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<Area> getAreasByConditions(AreaConditions conditions) {

        List<Area> resultList = areaMapper.getAreasByConditions(conditions);
        PageInfo<Area> pageInfo = new PageInfo(resultList);

        return new PageableResult<Area>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void saveArea(Area area) {
        if (area.getId() == null) {
            area.setStatus(StatusEnum.ACTIVED);
            areaMapper.insertArea(area);
        } else {
            areaMapper.updateArea(area);
        }
    }

    @Override
    public void disableArea(Integer id) {
        areaMapper.updateAreaStatus(id, StatusEnum.DISABLED);
    }

    @Override
    public void deleteArea(Integer id) {
        areaMapper.deleteArea(id);
    }


    /**
     * 工种
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<Job> getJobsByConditions(JobConditions conditions) {

        List<Job> resultList = jobMapper.getJobsByConditions(conditions);
        PageInfo<Job> pageInfo = new PageInfo(resultList);

        return new PageableResult<Job>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void saveJob(Job job) {
        if (job.getId() == null) {
            job.setStatus(StatusEnum.ACTIVED);
            jobMapper.insertJob(job);
        } else {
            jobMapper.updateJob(job);
        }
    }

    @Override
    public void disableJob(Integer id) {
        jobMapper.updateJobStatus(id, StatusEnum.DISABLED);
    }

    @Override
    public void deleteJob(Integer id) {
        jobMapper.deleteJob(id);
    }

    /**
     * **************** 单位 ****************
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<Company> getCompaniesByConditions(CompanyConditions conditions) {

        List<Company> resultList = companyMapper.getByConditions(conditions);
        PageInfo<Company> pageInfo = new PageInfo(resultList);

        return new PageableResult<Company>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }
    @Override
    public void saveCompany(Company company) {
        if (company.getId() == null) {
            company.setStatus(StatusEnum.ACTIVED);
            companyMapper.insert(company);
        } else {
            companyMapper.update(company);
        }
    }
    @Override
    public void disableCompany(Integer id) {
        companyMapper.updateStatus(id, StatusEnum.DISABLED);
    }
    @Override
    public void deleteCompany(Integer id) {
        companyMapper.delete(id);
    }

    /**
     * **************** 项目 ****************
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<ProjectDTO> getProjectsByConditions(ProjectConditions conditions) {

        List<ProjectDTO> resultList = projectMapper.getByConditions(conditions);
        PageInfo<ProjectDTO> pageInfo = new PageInfo(resultList);

        return new PageableResult<ProjectDTO>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }
    @Override
    public void saveProject(Project project) {
        //保存单位名称
        Company company = companyMapper.getById(project.getCompanyId());
        if(company == null){
            throw new EMSBusinessException("所选单位不存在,请确认!");
        }
        project.setCompanyName(company.getCompanyName());

        if (project.getId() == null) {
            project.setStatus(StatusEnum.ACTIVED);
            projectMapper.insert(project);
        } else {
            projectMapper.update(project);
        }
    }
    @Override
    public void disableProject(Integer id) {
        projectMapper.updateStatus(id, StatusEnum.DISABLED);
    }
    @Override
    public void deleteProject(Integer id) {
        projectMapper.delete(id);
    }
}
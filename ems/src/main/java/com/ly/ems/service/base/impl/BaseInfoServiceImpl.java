package com.ly.ems.service.base.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.dao.base.*;
import com.ly.ems.dao.base.mapper.*;
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
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.service.base.BaseInfoService;
import com.ly.ems.service.dispatch.DispatchService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class BaseInfoServiceImpl implements BaseInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInfoServiceImpl.class);

    @Autowired
    DispatchService dispatchService;

    @Autowired
    ExtendEmployeeMapper extendEmployeeMapper;
    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    ExtendGroupMapper extendGroupMapper;
    @Autowired
    GroupMapper groupMapper;

    @Autowired
    ExtendJobMapper extendJobMapper;
    @Autowired
    JobMapper jobMapper;

    @Autowired
    ExtendCompanyMapper extendCompanyMapper;
    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    ExtendProjectMapper extendProjectMapper;
    @Autowired
    ProjectMapper projectMapper;

    /**
     * 员工
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<EmployeeVo> getEmployeesByConditions(EmployeeConditions conditions) {

        List<EmployeeVo> resultList = extendEmployeeMapper.selectByConditions(conditions);
        PageInfo<EmployeeVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<EmployeeVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);
    }

    @Override
    public void saveEmployee(Employee employee) {
        // 先判断id是否重复
        if (this.checkEmployeeIdCardExist(employee.getId(), employee.getIdCard())) {
            throw new EMSRuntimeException("身份证已存在，请确认");
        }

        if (employee.getId() == null) {
            employee.setEnable(EnableEnum.ENABLED);
            employee.setCreateDate(new Date());
            employeeMapper.insertSelective(employee);
        } else {
            employeeMapper.updateByPrimaryKeySelective(employee);
        }
    }

    /**
     * 判断idCard是否已存在
     *
     * @param id
     * @param idCard
     */
    private boolean checkEmployeeIdCardExist(Integer id, String idCard) {
        Employee employee = new Employee();
        employee.setIdCard(idCard);
        List<Employee> employeeList = employeeMapper.select(employee);
        // 不存在记录
        if (employeeList == null || employeeList.size() <= 0) {
            return false;
        }

        // 新增的情况
        if (id == null || id == 0 || id == -1) {
            return true;
        }

        // 修改的情况，除了自己以外存在才算重复
        for (Employee emp : employeeList) {
            if (!id.equals(emp.getId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void disableEmployee(Integer id) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setEnable(EnableEnum.DISABLED);
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchInsertEmployees(List<Employee> employeeList) {
        // 与库中数据对比身份证号
        for (Employee employee : employeeList) {
            if (this.checkEmployeeIdCardExist(employee.getId(), employee.getIdCard())) {
                throw new EMSRuntimeException(String.format("导入员工失败，身份证号【%s】已存在员工库中", employee.getIdCard()));
            }
        }

        // 导入列表中查询
        for (int i = 0; i < employeeList.size(); i++) {
            for (int j = i + 1; j < employeeList.size(); j++) {
                if (employeeList.get(i).getIdCard().equals(employeeList.get(j).getIdCard())) {

                    throw new EMSRuntimeException(String.format("导入员工失败，导入列表中存在重复的身份证号【%s】", employeeList.get(i).getIdCard()));
                }
            }
        }

        for (Employee employee : employeeList) {
            employee.setCreateDate(new Date());
        }

        return extendEmployeeMapper.batchInsert(employeeList);
    }


    /**
     * 班组
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<GroupVo> getGroupsByConditions(GroupConditions conditions) {

        List<GroupVo> resultList = extendGroupMapper.selectByConditions(conditions);
        PageInfo<GroupVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<GroupVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void saveGroup(Group group, Integer projectId) {
        if (group.getId() == null) {
            group.setEnable(EnableEnum.ENABLED);
            groupMapper.insertSelective(group);
        } else {
            groupMapper.updateByPrimaryKeySelective(group);
        }

        if (projectId != null) {
            dispatchService.dispatchGroupToProjectByProjectId(group, projectId);
        }
    }

    @Override
    public void disableGroup(Integer id) {
        Group group = new Group();
        group.setId(id);
        group.setEnable(EnableEnum.DISABLED);
        groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public void deleteGroup(Integer id) {
        groupMapper.deleteByPrimaryKey(id);
    }


    /**
     * 工种
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<Job> getJobsByConditions(JobConditions conditions) {
        this.currentUser();
        List<Job> resultList = extendJobMapper.selectByConditions(conditions);
        PageInfo<Job> pageInfo = new PageInfo(resultList);

        return new PageableResult<Job>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void saveJob(Job job) {
        if (job.getId() == null) {
            job.setEnable(EnableEnum.ENABLED);

            jobMapper.insertSelective(job);
        } else {
            jobMapper.updateByPrimaryKeySelective(job);
        }
    }

    @Override
    public void disableJob(Integer id) {
        Job job = new Job();
        job.setId(id);
        job.setEnable(EnableEnum.DISABLED);
        jobMapper.updateByPrimaryKeySelective(job);
    }

    @Override
    public void deleteJob(Integer id) {
        jobMapper.deleteByPrimaryKey(id);
    }

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

    /**
     * **************** 项目 ****************
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<ProjectVo> getProjectsByConditions(ProjectConditions conditions) {

        List<ProjectVo> resultList = extendProjectMapper.selectByConditions(conditions);
        PageInfo<ProjectVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<ProjectVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void saveProject(Project project) {
        if (project.getId() == null) {
            project.setEnable(EnableEnum.ENABLED);
            projectMapper.insertSelective(project);
        } else {
            projectMapper.updateByPrimaryKeySelective(project);
        }
    }

    @Override
    public void disableProject(Integer id) {
        Project project = new Project();
        project.setId(id);
        project.setEnable(EnableEnum.DISABLED);
        projectMapper.updateByPrimaryKeySelective(project);
    }

    @Override
    public void deleteProject(Integer id) {
        projectMapper.deleteByPrimaryKey(id);
    }

    private String currentUser() {

        Subject currentUser = SecurityUtils.getSubject();
        return "";
    }
}
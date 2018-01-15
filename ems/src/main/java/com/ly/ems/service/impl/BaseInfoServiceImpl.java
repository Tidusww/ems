package com.ly.ems.service.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.dao.base.AreaMapper;
import com.ly.ems.dao.base.EmployeeMapper;
import com.ly.ems.dao.base.GroupMapper;
import com.ly.ems.dao.base.JobMapper;
import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.area.AreaConditions;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeDTO;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
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
}
package com.ly.ems.service.job.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.dao.base.*;
import com.ly.ems.dao.base.mapper.*;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectVo;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.service.dispatch.DispatchService;
import com.ly.ems.service.job.JobService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JobServiceImpl implements JobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    DispatchService dispatchService;

    @Autowired
    ExtendJobMapper extendJobMapper;
    @Autowired
    JobMapper jobMapper;



    /**
     * 工种
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<Job> getJobsByConditions(JobConditions conditions) {
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

}
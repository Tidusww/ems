package com.ly.ems.dao.base.mapper;

import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobExample;
import java.util.List;
import tk.mybatis.mapper.common.BaseMapper;

public interface JobMapper extends BaseMapper<Job> {
    List<Job> selectByExample(JobExample example);
}
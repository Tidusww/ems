package com.ly.ems.dao.base;

import com.ly.ems.core.mybatis.dao.ConditionMapper;
import com.ly.ems.dao.base.mapper.JobMapper;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface ExtendJobMapper extends ConditionMapper<Job, JobConditions> {

}

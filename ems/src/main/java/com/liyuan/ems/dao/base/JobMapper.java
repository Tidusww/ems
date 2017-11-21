package com.liyuan.ems.dao.base;

import com.liyuan.ems.model.base.job.Job;
import com.liyuan.ems.model.base.job.JobConditions;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface JobMapper {

    /**
     * 根据id查找job
     *
     * @param id
     * @return
     */
    Job getJobById(@Param("id") Integer id);

    /**
     * 条件查询(可分页)
     * @param conditions
     * @return
     */
    List<Job> getJobsByConditions(JobConditions conditions);
}
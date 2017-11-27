package com.ly.ems.dao.base;

import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.common.constant.StatusEnum;
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

    /**
     * 新增Job
     * @param job
     */
    void insertJob(Job job);

    /**
     * 修改Job
     * @param job
     */
    void updateJob(Job job);

    /**
     * 修改Job状态
     * @param id
     */
    void updateJobStatus(@Param("id")Integer id, @Param("status")StatusEnum statusEnum);

    /**
     * 删除Job
     * @param id
     */
    void deleteJob(@Param("id")Integer id);
}

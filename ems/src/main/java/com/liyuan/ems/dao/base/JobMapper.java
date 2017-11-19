package com.liyuan.ems.dao.base;

import com.liyuan.ems.model.base.Job;
import org.apache.ibatis.annotations.Param;

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
}

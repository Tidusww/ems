package com.ly.ems.model.base.job;

/**
 * Created by tidus on 2017/10/31.
 */
public class JobConditions extends Job {

    public Integer current;
    public Integer pageSize;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}

package com.ly.ems.model.config;

/**
 * Created by tidus on 2018/10/25.
 */
public class SystemConfigCondition extends SystemConfigVo {

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

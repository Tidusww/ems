package com.ly.ems.model.dispatch;

/**
 * Created by tidus on 2018/9/23.
 */
public class DispatchRelCondition extends DispatchRel {

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

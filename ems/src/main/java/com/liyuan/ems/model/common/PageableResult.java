package com.liyuan.ems.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mark on 17/5/23.
 */
public class PageableResult<T> implements Serializable {

    public int total;
    public int current;
    public int pageSize;
    public List<T> dataSource;

    public PageableResult() {
        super();
    }

	public PageableResult(int total, int current, int pageSize, List<T> dataSource) {
		super();
		this.total = total;
		this.current = current;
		this.pageSize = pageSize;
		this.dataSource = dataSource;
	}

	@Override
	public String toString() {
		return "PageableResult [total=" + total + ", current=" + current + ", pageSize=" + pageSize + ", dataSource="
				+ dataSource + "]";
	}



    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getDataSource() {
        return dataSource;
    }

    public void setDataSource(List<T> rows) {
        this.dataSource = rows;
    }


}

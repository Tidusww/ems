package com.liyuan.ems.core.mybatis.pagehelper;

/**
 * Created by tidus on 2017/10/31.
 */
public class PageableCondition {

	public int current;
	public int pageSize;

	public PageableCondition(int current, int pageSize) {
		this.current = current;
		this.pageSize = pageSize;
	}

	public PageableCondition() {

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

}

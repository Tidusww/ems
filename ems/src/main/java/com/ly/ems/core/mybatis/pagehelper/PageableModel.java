package com.ly.ems.core.mybatis.pagehelper;


/**
 * Created by tidus on 2017/10/31.
 * 需要分页的Model
 */
@Deprecated
public class PageableModel {

	public int current;
	public int pageSize;

	public PageableModel(int current, int pageSize) {
		this.current = current;
		this.pageSize = pageSize;
	}

	public PageableModel() {

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

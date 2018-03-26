package com.ly.ems.core.mybatis.pagehelper;

import com.ly.ems.model.BaseModel;

/**
 * Created by tidus on 2017/10/31.
 * 需要分页的Model
 */
public class PageableModel extends BaseModel{

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

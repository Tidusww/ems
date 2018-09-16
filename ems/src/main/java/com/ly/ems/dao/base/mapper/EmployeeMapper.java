package com.ly.ems.dao.base.mapper;

import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeExample;
import java.util.List;
import tk.mybatis.mapper.common.BaseMapper;

public interface EmployeeMapper extends BaseMapper<Employee> {
    List<Employee> selectByExample(EmployeeExample example);
}
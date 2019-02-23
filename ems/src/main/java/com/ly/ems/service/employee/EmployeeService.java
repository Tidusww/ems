package com.ly.ems.service.employee;

import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeVo;
import com.ly.ems.model.common.PageableResult;

import java.util.List;


/**
 * 基础信息
 * Created by tidus on 2017/11/19.
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface EmployeeService {
    /**
     * **************** 1.员工 ****************
     * @param conditions
     * @return
     */
    PageableResult<EmployeeVo> getEmployeesByConditions(EmployeeConditions conditions);
    void saveEmployee(Employee employee);
    void disableEmployee(Integer id);
    void deleteEmployee(Integer id);
    int batchInsertEmployees(List<Employee> employeeList);

}

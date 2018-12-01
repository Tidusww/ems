package com.ly.ems.dao.base;

import com.ly.ems.core.mybatis.dao.ConditionMapper;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface ExtendEmployeeMapper extends ConditionMapper<EmployeeVo, EmployeeConditions> {

    /**
     * 根据月份查询存在派遣关系的员工
     * @param month
     * @return
     */
    List<EmployeeVo> getDispatchedEmployeeByMonth(@Param("month")Date month);

    /**
     * 批量插入员工信息
     * @param list
     */
    int batchInsert(@Param("list") List<Employee> list);
}

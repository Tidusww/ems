package com.ly.ems.dao.base;

import com.ly.ems.core.mybatis.dao.ConditionMapper;
import com.ly.ems.dao.base.mapper.EmployeeMapper;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeVo;

/**
 * Created by tidus on 2017/11/19.
 */
public interface ExtendEmployeeMapper extends ConditionMapper<EmployeeVo, EmployeeConditions> {

}

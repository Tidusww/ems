package com.ly.ems.dao.base;

import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeDTO;
import com.ly.ems.model.common.constant.StatusEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2017/11/19.
 */
public interface EmployeeMapper {

    /**
     * 根据id查找job
     *
     * @param id
     * @return
     */
    EmployeeDTO getEmployeeById(@Param("id") Integer id);

    /**
     * 条件查询(可分页)
     *
     * @param conditions
     * @return
     */
    List<EmployeeDTO> getEmployeesByConditions(EmployeeConditions conditions);

    /**
     * 新增Area
     *
     * @param area
     */
    //TODO
    void insertEmployee(Employee area);

    /**
     * 修改Area
     *
     * @param area
     */
    //TODO
    void updateEmployee(Employee area);

    /**
     * 修改Area状态
     *
     * @param id
     */
    void updateEmployeeStatus(@Param("id") Integer id, @Param("status") StatusEnum statusEnum);

    /**
     * 删除Area
     *
     * @param id
     */
    void deleteEmployee(@Param("id") Integer id);

}
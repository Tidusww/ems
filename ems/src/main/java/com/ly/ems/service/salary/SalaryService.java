package com.ly.ems.service.salary;

import com.ly.ems.model.attendance.AttendanceVo;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.salary.Salary;
import com.ly.ems.model.salary.SalaryCondition;
import com.ly.ems.model.salary.SalaryVo;

import java.util.Date;
import java.util.List;

/**
 * Created by tidus on 2018/10/2.
 */
public interface SalaryService {

    /**
     * 查询工资明细信息，用于导出工资明细表、工资管理页
     * @param conditions
     * @return
     */
    PageableResult<SalaryVo> getSalaries(SalaryCondition conditions);

    /**
     * 按班组查询工资汇总信息，用于导出工资汇总表
     * @param conditions
     * @return
     */
    PageableResult<SalaryVo> getSalarySummary(SalaryCondition conditions);

    /**
     * 查询工资简要信息，用于导出工资发放表
     * @param conditions
     * @return
     */
    PageableResult<SalaryVo> getSalaryDispatch(SalaryCondition conditions);

    /**
     * 生成工资信息
     * @param conditions
     */
    void generateSalaries(SalaryCondition conditions);

    /**
     * 更新工资信息，并重新计算该条工资的内容
     * 可更新的字段有：
     * 【2】日社保补贴、【3】日住房补贴、【4】日高温津贴、【5】其他收入
     * 【7】社保费、【8】住房公积金、【10】其他扣除、【12】单位社保、【13】单位公积金
     *
     * @param salary
     */
    void updateSalary(Salary salary, Date month);


    void saveSalaries(List<Salary> salaryList, String monthString);


    /**
     * 根据考勤记录和工资记录重新计算
     * @param attendanceVo
     * @param salary
     * @param month
     * @return
     */
    Salary calculateSalary(AttendanceVo attendanceVo, Salary salary, Date month);
}

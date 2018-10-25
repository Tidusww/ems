package com.ly.ems.service.salary;

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


    PageableResult<SalaryVo> getSalaries(SalaryCondition conditions);

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


}

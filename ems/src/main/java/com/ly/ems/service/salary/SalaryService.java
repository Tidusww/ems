package com.ly.ems.service.salary;

import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.salary.Salary;
import com.ly.ems.model.salary.SalaryCondition;
import com.ly.ems.model.salary.SalaryVo;

import java.util.List;

/**
 * Created by tidus on 2018/10/2.
 */
public interface SalaryService {


    PageableResult<SalaryVo> getSalaries(SalaryCondition conditions);

    void generateSalaries(SalaryCondition conditions);

    void saveSalaries(List<Salary> salaryList, String monthString);


}

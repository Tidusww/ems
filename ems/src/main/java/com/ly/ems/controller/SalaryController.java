package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.salary.SalaryCondition;
import com.ly.ems.model.salary.SalaryVo;
import com.ly.ems.service.salary.SalaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tidus on 2018/10/2.
 */
@Controller
@RequestMapping(value = "/salary", name = "工资信息管理")
public class SalaryController {
    private Logger logger = LoggerFactory.getLogger(SalaryController.class);

    @Autowired
    SalaryService salaryService;

    @ResponseBody
    @RequestMapping(value = "/get", name = "分页查询工资信息")
    public AjaxResult getSalaries(SalaryCondition conditions) {
        PageableResult<SalaryVo> pageableResult = salaryService.getSalaries(conditions);
        return AjaxResult.success(pageableResult);
    }

    /**
     * 根据所选月份的考勤情况生成工资信息
     * @param conditions
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/generate", method = RequestMethod.POST, name = "生成工资信息")
    public AjaxResult generateSalaries(SalaryCondition conditions) {
        salaryService.generateSalaries(conditions);
        return AjaxResult.success("生成工资信息成功");
    }
}

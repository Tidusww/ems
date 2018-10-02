package com.ly.ems.service.salary.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.dao.salary.ExtendSalaryMapper;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.salary.Salary;
import com.ly.ems.model.salary.SalaryCondition;
import com.ly.ems.model.salary.SalaryVo;
import com.ly.ems.model.salary.constant.SalaryConstant;
import com.ly.ems.service.salary.SalaryService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tidus on 2018/10/2.
 */
@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    ExtendSalaryMapper extendSalaryMapper;


    @Override
    public PageableResult<SalaryVo> getSalaries(SalaryCondition conditions) {
        // 必须选定月份
        Date attendanceMonth = conditions.getMonth();
        if (attendanceMonth == null) {
            throw new EMSBusinessException("查看工资数据前必须先选定月份!");
        }

        // 考勤数据必须存在
        String monthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        if (!this.checkSalaryExist(monthString)) {
            throw new EMSBusinessException(String.format("月份%s的考勤数据不存在，请先生成数据!", monthString));
        }

        // 查询数据
        String salaryTableName = SalaryConstant.Salary_TABLE_NAME_PRE + monthString;
        List<SalaryVo> resultList = extendSalaryMapper.getSalariesByConditions(conditions, salaryTableName);
        PageInfo<SalaryVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<SalaryVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    @Override
    public void generateSalaries(SalaryCondition conditions) {

    }

    @Override
    public void saveSalaries(List<Salary> salaryList, String monthString) {

    }




    /**
     * 查询指定月份的工资数据是否存在
     *
     * @param monthString
     */
    private boolean checkSalaryExist(String monthString) {
        String salaryTableName = SalaryConstant.Salary_TABLE_NAME_PRE + monthString;
        int exists = extendSalaryMapper.isExistSalaryTable(salaryTableName);
        return exists > 0;
    }
}

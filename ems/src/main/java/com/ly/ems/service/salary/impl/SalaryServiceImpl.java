package com.ly.ems.service.salary.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.dao.base.mapper.JobMapper;
import com.ly.ems.dao.salary.ExtendSalaryMapper;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceVo;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.salary.Salary;
import com.ly.ems.model.salary.SalaryCondition;
import com.ly.ems.model.salary.SalaryVo;
import com.ly.ems.model.salary.constant.SalaryConstant;
import com.ly.ems.service.attendance.AttendanceService;
import com.ly.ems.service.config.SystemConfigService;
import com.ly.ems.service.salary.SalaryService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tidus on 2018/10/2.
 */
@Service
public class SalaryServiceImpl implements SalaryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryServiceImpl.class);

    @Autowired
    SystemConfigService systemConfigService;
    @Autowired
    AttendanceService attendanceService;

    @Autowired
    JobMapper jobMapper;
    @Autowired
    ExtendSalaryMapper extendSalaryMapper;


    /**
     * 分页查询指定月份的工资数据
     *
     * @param conditions
     * @return
     */
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
            throw new EMSBusinessException(String.format("月份%s的工资数据不存在，请先生成数据!", monthString));
        }

        // 查询数据
        String salaryTableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
        List<SalaryVo> resultList = extendSalaryMapper.getSalariesByConditions(conditions, salaryTableName);
        PageInfo<SalaryVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<SalaryVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    /**
     * 根据指定月份的考勤数据，生成工资数据
     *
     * @param conditions
     */
    @Override
    public void generateSalaries(SalaryCondition conditions) {
        Date month = conditions.getMonth();
        if (month == null) {
            throw new EMSBusinessException("生成工资数据前必须先选定月份!");
        }

        String monthString = DateFormatUtils.format(month, DateUtil.YYYYMM);
        if (this.checkSalaryExist(monthString)) {
            throw new EMSBusinessException(String.format("月份%s的工资数据已存在!", monthString));
        }

        try {
            this.createSalaryTable(month);
        } catch (Exception e) {
            LOGGER.error("创建考勤表失败", e);
            throw new EMSBusinessException("创建考勤表失败");
        }

        // 获取考勤数据
        AttendanceConditions attendanceConditions = new AttendanceConditions();
        attendanceConditions.setAttendanceMonth(month);
        PageableResult<AttendanceVo> pageableResult = attendanceService.getAttendances(attendanceConditions);
        List<AttendanceVo> attendanceVoList = pageableResult.getDataSource();


        LOGGER.info(String.format("共查询到%d条考勤信息，开始生成%s的工资信息...", attendanceVoList.size(), monthString));
        long startTime = System.currentTimeMillis();

        // 分页生成
        int totalSize = 0;
        boolean isContinue = true;
        int pageIndex = 1;
        int pageSize = 500;
        while (isContinue) {
            // 分页获取考勤信息
            int formIndex = (pageIndex - 1) * pageSize;
            int toIndex = pageIndex * pageSize;
            if (toIndex > attendanceVoList.size()) {
                toIndex = attendanceVoList.size();
                isContinue = false;
            }
            List<AttendanceVo> attendanceVoPage = attendanceVoList.subList(formIndex, toIndex);

            // 为考勤信息生成工资信息
            List<Salary> salaryList = new ArrayList<Salary>();
            for (AttendanceVo attendanceVo : attendanceVoPage) {
                Salary salary = this.generateSalaryForAttendance(attendanceVo, month);
                salaryList.add(salary);
            }
            String salaryTableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
            extendSalaryMapper.batchInsert(salaryTableName, salaryList);
            totalSize += salaryList.size();
        }

        LOGGER.info(String.format("生成工资信息完毕，共生成%d条工资信息，耗时%dms。", totalSize, System.currentTimeMillis() - startTime));

    }

    @Override
    public void saveSalaries(List<Salary> salaryList, String monthString) {

    }


    /**
     * util-1
     * 查询指定月份的工资数据是否存在
     *
     * @param monthString
     */
    private boolean checkSalaryExist(String monthString) {
        String salaryTableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
        int exists = extendSalaryMapper.isExistSalaryTable(salaryTableName);
        return exists > 0;
    }

    /**
     * util-2
     * 创建指定月份的考勤表
     *
     * @param month
     * @return
     */
    private void createSalaryTable(Date month) {
        String monthString = DateFormatUtils.format(month, DateUtil.YYYYMM);
        String tableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
        extendSalaryMapper.createSalaryTable(tableName);
    }

    /**
     * util-3
     * 为每条出勤记录生成工资记录
     *
     * @param attendanceVo
     * @param attendanceMonth
     */
    private Salary generateSalaryForAttendance(AttendanceVo attendanceVo, Date attendanceMonth) {
        Salary salary = new Salary();
        try {
            Job job = jobMapper.selectByPrimaryKey(attendanceVo.getJobId());
            double jobSalary = job.getSalary().doubleValue();
            Integer attendanceDays = attendanceVo.getAttendanceDays();

            // 1.基本工资：本地最低月工资标准（手动）
            double basicSalary = systemConfigService.getBasicSalary();
            // 2.加班工资：工种工资*（总出勤天数-21.75）*2
            double overtimeSalary = jobSalary * (attendanceDays - 21.75f) * 2;
            // 3.计量工资：工种工资*出勤天数-基本工资-加班工资
            double calculateSalary = jobSalary * attendanceDays - basicSalary - overtimeSalary;
            // 4.高温费：不同地区不同时段，出勤天数*日高温费标准（手动）
            double hotAllowance = 0.d;
            if (systemConfigService.isHotAllowanceDate(attendanceMonth)) {
                hotAllowance = attendanceDays * systemConfigService.getHotAllowance();
            }
            // 5.社保补贴
            double socialSecurityAllowance = 30 * systemConfigService.getSocialSecurityAllowance();
            // 6.住房补贴
            double houseFundAllowance = 30 * systemConfigService.getHouseFundAllowance();
            // 7.其他收入
            double otherIncome = 0.d;
            // 8.应付工资(税前)
            double payableSalary = basicSalary + overtimeSalary + calculateSalary + hotAllowance + socialSecurityAllowance + houseFundAllowance + otherIncome;

            // 9.个人部分社保
            double personalSocialSecurityAllowance = 0.d;
            // 10.个人部分公积金
            double personalHouseFundAllowance = 0.d;

            // 11.其他扣除
            double otherDeduction = 0.d;

            // 12.实发工资：【8】-【9】-【10】-【11】-【应付个税】
            // 个税
            double payTaxes = this.getPayTaxes(payableSalary);
            double realSalary = payableSalary - personalSocialSecurityAllowance - personalHouseFundAllowance - otherDeduction - payTaxes;

            // 13.单位社保
            double companySocialSecurityAllowance = 0.d;
            // 14.单位公积金
            double companyHouseFundAllowance = 0.d;

            // 1
            salary.setBasicSalary(new BigDecimal(basicSalary));
            salary.setOvertimeSalary(new BigDecimal(overtimeSalary));
            salary.setCalculateSalary(new BigDecimal(calculateSalary));
            salary.setHotAllowance(new BigDecimal(hotAllowance));
            salary.setSocialSecurityAllowance(new BigDecimal(socialSecurityAllowance));
            salary.setHouseFundAllowance(new BigDecimal(houseFundAllowance));
            salary.setOtherIncome(new BigDecimal(otherIncome));
            salary.setPayableSalary(new BigDecimal(payableSalary));
            // 2
            salary.setPersonalSocialSecurity(new BigDecimal(personalSocialSecurityAllowance));
            salary.setPersonalHouseFund(new BigDecimal(personalHouseFundAllowance));
            salary.setOtherDeduction(new BigDecimal(otherDeduction));
            salary.setPayTaxes(new BigDecimal(payTaxes));
            salary.setRealSalary(new BigDecimal(realSalary));
            // 3
            salary.setCompanySocialSecurity(new BigDecimal(companySocialSecurityAllowance));
            salary.setCompanyHouseFund(new BigDecimal(companyHouseFundAllowance));

            salary.setEmployeeId(attendanceVo.getEmployeeId());
            salary.setEnable(EnableEnum.ENABLED);

            return salary;
        } catch (Exception e) {
            LOGGER.error(String.format("生成工资信息失败，出勤记录id：【%d】", attendanceVo.getId()), e);
        }
        return salary;
    }

    /**
     * util-4
     * 根据应付工资（税前）计算应付个税
     * @param payableSalary
     * @return
     */
    private double getPayTaxes(double payableSalary){
        // 应纳税所得额
        double payableTaxesSalary = payableSalary - 5000.d;
        if(payableTaxesSalary < 0) {
            return 0;
        } else if(payableTaxesSalary < 3000.d) {
            return payableTaxesSalary * 0.03d;
        } else if(payableTaxesSalary < 12000.d) {
            return payableTaxesSalary * 0.10d - 210;
        } else if(payableTaxesSalary < 25000.d) {
            return payableTaxesSalary * 0.20d - 1410;
        } else if(payableTaxesSalary < 35000.d) {
            return payableTaxesSalary * 0.25d - 2660;
        } else if(payableTaxesSalary < 55000.d) {
            return payableTaxesSalary * 0.30d - 4410;
        } else if(payableTaxesSalary < 80000.d) {
            return payableTaxesSalary * 0.35d - 7160;
        } else {
            return payableTaxesSalary * 0.45d - 15160;
        }
    }

}

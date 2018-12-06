package com.ly.ems.service.salary.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.dao.base.mapper.JobMapper;
import com.ly.ems.dao.salary.ExtendSalaryMapper;
import com.ly.ems.dao.salary.SalaryMapper;
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
import org.springframework.validation.ValidationUtils;

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
    SalaryMapper salaryMapper;
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
            throw new EMSRuntimeException("查看工资数据前必须先选定月份!");
        }

        // 考勤数据必须存在
        String monthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        if (!this.checkSalaryExist(monthString)) {
            throw new EMSRuntimeException(String.format("月份%s的工资数据不存在，请先生成数据!", monthString));
        }

        // 查询数据
        String salaryTableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
        List<SalaryVo> resultList = extendSalaryMapper.getSalariesByConditions(conditions, salaryTableName);
        PageInfo<SalaryVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<SalaryVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    /**
     * 按班组查询工资汇总信息
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<SalaryVo> getSalarySummary(SalaryCondition conditions) {
        // 必须选定月份
        Date attendanceMonth = conditions.getMonth();
        if (attendanceMonth == null) {
            throw new EMSRuntimeException("请先选定月份!");
        }

        // 考勤数据必须存在
        String monthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        if (!this.checkSalaryExist(monthString)) {
            throw new EMSRuntimeException(String.format("月份%s的工资数据不存在，请先生成数据!", monthString));
        }

        // 查询数据
        String salaryTableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
        List<SalaryVo> resultList = extendSalaryMapper.getSalarySummaryByConditions(conditions, salaryTableName);
        PageInfo<SalaryVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<SalaryVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    /**
     * 查询工资简要信息，用于导出工资发放表
     * @param conditions
     * @return
     */
    public PageableResult<SalaryVo> getSalaryDispatch(SalaryCondition conditions) {
        // 必须选定月份
        Date attendanceMonth = conditions.getMonth();
        if (attendanceMonth == null) {
            throw new EMSRuntimeException("导出工资发放表时请先选定月份");
        }

        if(conditions.getGroupId() == null) {
            throw new EMSRuntimeException("导出工资发放表时请指定班组");
        }


        // 考勤数据必须存在
        String monthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        if (!this.checkSalaryExist(monthString)) {
            throw new EMSRuntimeException(String.format("月份%s的工资数据不存在，请先生成数据!", monthString));
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
            throw new EMSRuntimeException("生成工资数据前必须先选定月份!");
        }

        String monthString = DateFormatUtils.format(month, DateUtil.YYYYMM);
        if (this.checkSalaryExist(monthString)) {
            throw new EMSRuntimeException(String.format("月份%s的工资数据已存在!", monthString));
        }

        try {
            this.createSalaryTable(month);
        } catch (Exception e) {
            LOGGER.error("创建工资表失败", e);
            throw new EMSRuntimeException("创建工资表失败");
        }

        try {
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
                    // https://www.processon.com/mindmap/5b33aea5e4b063f71f4bceb5
                    Salary salary = this.generateSalaryForAttendance(attendanceVo, month);
                    if (salary != null) {
                        salaryList.add(salary);
                    }
                }
                if (!salaryList.isEmpty()) {
                    String salaryTableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
                    extendSalaryMapper.batchInsert(salaryTableName, salaryList);
                }
                totalSize += salaryList.size();
            }

            LOGGER.info(String.format("生成工资信息完毕，共生成%d条工资信息，耗时%dms。", totalSize, System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            LOGGER.error("生成工资信息失败", e);
            this.dropSalaryTable(month);
            throw new EMSRuntimeException("生成工资信息失败");
        }


    }

    /**
     * 更新工资信息，并重新计算该条工资的内容
     * 可更新的字段有：
     * 【2】日社保补贴、【3】日住房补贴、【4】日高温津贴、【5】其他收入
     * 【7】社保费、【8】住房公积金、【10】其他扣除、【12】单位社保、【13】单位公积金
     *
     * @param salary
     */
    @Override
    public void updateSalary(Salary salary, Date month) {
        try {
            String monthString = DateFormatUtils.format(month, DateUtil.YYYYMM);
            String salaryTableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
            Salary updateSalary = this.calculateSalary(salary, month);
            int row = extendSalaryMapper.updateSalaryById(salaryTableName, updateSalary);
            if (row != 1) {
                LOGGER.error(String.format("expected update row should be 1 but found %d", row));
                throw new EMSRuntimeException("更新工资信息失败");
            }
        } catch (Exception e) {
            LOGGER.error(String.format("计算工资信息失败，工资记录id：【%d】", salary.getId()), e);
            throw new EMSRuntimeException("更新工资信息失败");
        }
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
     * util-2.1
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
     * util-2.2
     * 删除指定月份的考勤表
     *
     * @param month
     * @return
     */
    private void dropSalaryTable(Date month) {
        String monthString = DateFormatUtils.format(month, DateUtil.YYYYMM);
        String tableName = SalaryConstant.SALARY_TABLE_NAME_PRE + monthString;
        extendSalaryMapper.dropSalaryTable(tableName);
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
            Integer attendanceDays = attendanceVo.getAttendanceDays();

            // 1.日工资=工种的工资标准（自设参数）
            Job job = jobMapper.selectByPrimaryKey(attendanceVo.getJobId());
            double dailySalary = job.getSalary().doubleValue();
            // 2.日社保补贴=40元/天（40元/天为自设参数）
            double socialSecurityAllowance = systemConfigService.getSocialSecurityAllowance();
            // 3.日住房补贴=10元/天（10元/天为自设参数）
            double houseFundAllowance = systemConfigService.getHouseFundAllowance();
            // 4.日高温津贴：7.5元/天（自设参数）
            double hotAllowance = systemConfigService.isHotAllowanceDate(attendanceMonth) ? systemConfigService.getHotAllowance() : 0.d;
            // 5.其他收入=应付工资-（日工资+日社保补贴+日住房补贴+日高温津贴）*工作天数
            double otherIncome = 0.d;

            // 6.应付工资=（日工资+日社保补贴+日住房补贴+日高温津贴）*工作天数+其他收入
            double payableSalary = (dailySalary + socialSecurityAllowance + houseFundAllowance + hotAllowance) * attendanceDays + otherIncome;

            // 7.个人部分社保
            double personalSocialSecurityAllowance = 0.d;
            // 8.个人部分公积金
            double personalHouseFundAllowance = 0.d;
            // 9.个税
            double payTaxes = this.getPayTaxes(payableSalary);
            // 10.其他扣除
            double otherDeduction = 0.d;
            // 11.实发工资：【6】-【7】-【8】-【9】-【10】
            double realSalary = payableSalary - personalSocialSecurityAllowance - personalHouseFundAllowance - payTaxes - otherDeduction;

            // 12.单位社保
            double companySocialSecurityAllowance = 0.d;
            // 13.单位公积金
            double companyHouseFundAllowance = 0.d;

            // 1
            salary.setAttendanceDays(attendanceDays);
            salary.setDailySalary(new BigDecimal(dailySalary));
            salary.setSocialSecurityAllowance(new BigDecimal(socialSecurityAllowance));
            salary.setHouseFundAllowance(new BigDecimal(houseFundAllowance));
            salary.setHotAllowance(new BigDecimal(hotAllowance));
            salary.setOtherIncome(new BigDecimal(otherIncome));
            salary.setPayableSalary(new BigDecimal(payableSalary));
            // 2
            salary.setPersonalSocialSecurity(new BigDecimal(personalSocialSecurityAllowance));
            salary.setPersonalHouseFund(new BigDecimal(personalHouseFundAllowance));
            salary.setPayTaxes(new BigDecimal(payTaxes));
            salary.setOtherDeduction(new BigDecimal(otherDeduction));
            salary.setRealSalary(new BigDecimal(realSalary));
            // 3
            salary.setCompanySocialSecurity(new BigDecimal(companySocialSecurityAllowance));
            salary.setCompanyHouseFund(new BigDecimal(companyHouseFundAllowance));

            salary.setEmployeeId(attendanceVo.getEmployeeId());
            salary.setEnable(EnableEnum.ENABLED);

            return salary;
        } catch (Exception e) {
            LOGGER.error(String.format("生成工资信息失败，出勤记录id：【%d】", attendanceVo.getId()), e);
            return null;
        }
    }

    /**
     * util-4
     * 为每条出勤记录生成工资记录
     *
     * @param salary
     */
    private Salary calculateSalary(Salary salary, Date month) {
        // TODO 从数据库获取出勤天数
        Integer attendanceDays = salary.getAttendanceDays();

        // 6.应付工资=（日工资+日社保补贴+日住房补贴+日高温津贴）*工作天数+其他收入
        double payableSalary = (salary.getDailySalary().doubleValue()
                + salary.getSocialSecurityAllowance().doubleValue()
                + (systemConfigService.isHotAllowanceDate(month) ? salary.getHotAllowance().doubleValue() : 0.d)
                + salary.getHotAllowance().doubleValue()) * attendanceDays;
        payableSalary += salary.getOtherIncome().doubleValue();

        // 7.个人部分社保
        double personalSocialSecurityAllowance = salary.getPersonalSocialSecurity().doubleValue();
        // 8.个人部分公积金
        double personalHouseFundAllowance = salary.getPersonalHouseFund().doubleValue();
        // 9.个税
        double payTaxes = this.getPayTaxes(payableSalary);
        // 10.其他扣除
        double otherDeduction = salary.getOtherDeduction().doubleValue();

        // 11.实发工资：【6】-【7】-【8】-【9】-【10】
        double realSalary = payableSalary - personalSocialSecurityAllowance - personalHouseFundAllowance - payTaxes - otherDeduction;


        /**
         * 设置需要更新的工资信息
         */
        Salary updateSalary = new Salary();
        // 确保修改的字段在控制范围内
        updateSalary.setId(salary.getId());
        // 【2】~【5】
        updateSalary.setSocialSecurityAllowance(salary.getSocialSecurityAllowance());
        updateSalary.setHouseFundAllowance(salary.getHouseFundAllowance());
        updateSalary.setHotAllowance(salary.getHotAllowance());
        updateSalary.setOtherIncome(salary.getOtherIncome());
        // 【6】
        updateSalary.setPayableSalary(new BigDecimal(payableSalary));
        // 【7】~【10】
        updateSalary.setPersonalSocialSecurity(new BigDecimal(personalSocialSecurityAllowance));
        updateSalary.setPersonalHouseFund(new BigDecimal(personalHouseFundAllowance));
        updateSalary.setPayTaxes(new BigDecimal(payTaxes));
        updateSalary.setOtherDeduction(new BigDecimal(otherDeduction));
        // 【11】
        updateSalary.setRealSalary(new BigDecimal(realSalary));
        // 【12】、【13】
        updateSalary.setCompanySocialSecurity(salary.getCompanySocialSecurity());
        updateSalary.setCompanyHouseFund(salary.getCompanyHouseFund());

        return updateSalary;
    }

    /**
     * util-5
     * 根据应付工资（税前）计算应付个税
     *
     * @param payableSalary
     * @return
     */
    private double getPayTaxes(double payableSalary) {
        // 应纳税所得额
        double payableTaxesSalary = payableSalary - 5000.d;
        if (payableTaxesSalary < 0) {
            return 0;
        } else if (payableTaxesSalary < 3000.d) {
            return payableTaxesSalary * 0.03d;
        } else if (payableTaxesSalary < 12000.d) {
            return payableTaxesSalary * 0.10d - 210;
        } else if (payableTaxesSalary < 25000.d) {
            return payableTaxesSalary * 0.20d - 1410;
        } else if (payableTaxesSalary < 35000.d) {
            return payableTaxesSalary * 0.25d - 2660;
        } else if (payableTaxesSalary < 55000.d) {
            return payableTaxesSalary * 0.30d - 4410;
        } else if (payableTaxesSalary < 80000.d) {
            return payableTaxesSalary * 0.35d - 7160;
        } else {
            return payableTaxesSalary * 0.45d - 15160;
        }
    }


}

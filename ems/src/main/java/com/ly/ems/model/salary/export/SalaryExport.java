package com.ly.ems.model.salary.export;

import com.ly.ems.common.utils.file.ExcelAttribute;
import com.ly.ems.model.common.constant.EnableEnum;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tidus on 2018/10/3.
 */
public class SalaryExport {
    /**
     * 员工id
     */
    @ExcelAttribute(name = "员工姓名", column = "A")
    private String employeeName;

    /**
     * 1、最低月基本工资
     */
    @Column(name = "`basicSalary`")
    private BigDecimal basicSalary;

    /**
     * 2、加班工资：工种工资*（总出勤天数-21.75）*2
     */
    @Column(name = "`overtime_salary`")
    private BigDecimal overtimeSalary;

    /**
     * 3、计量工资：工种工资*出勤天数-基本工资-加班工资
     */
    @Column(name = "`calculate_salary`")
    private BigDecimal calculateSalary;

    /**
     * 4、高温费：总出勤天数*每日高温费
     */
    @Column(name = "`hot_allowance`")
    private BigDecimal hotAllowance;

    /**
     * 5、社保补贴：30*每日社保
     */
    @Column(name = "`social_security_allowance`")
    private BigDecimal socialSecurityAllowance;

    /**
     * 6、住房补贴：30*每日住房
     */
    @Column(name = "`house_fund_allowance`")
    private BigDecimal houseFundAllowance;

    /**
     * 7、其他收入（手动）
     */
    @Column(name = "`other_income`")
    private BigDecimal otherIncome;

    /**
     * 8、应付工资（含税工资）：【1】+【2】+【3】+【4】+【5】+【6】+【7】
     */
    @Column(name = "`payable_salary`")
    private BigDecimal payableSalary;

    /**
     * 9、个人部分社保(手动)
     */
    @Column(name = "`personal_social_security`")
    private BigDecimal personalSocialSecurity;

    /**
     * 10、个人部分公积金(手动)
     */
    @Column(name = "`personal_house_fund`")
    private BigDecimal personalHouseFund;

    /**
     * 11、其他扣除：借支或扣费（手动）
     */
    @Column(name = "`other_deduction`")
    private BigDecimal otherDeduction;

    /**
     * 应付个税：最新税法
     */
    @Column(name = "`pay_taxes`")
    private BigDecimal payTaxes;

    /**
     * 12、实发工资：【8】-【9】-【10】-【11】-【应付个税】
     */
    @Column(name = "`real_salary`")
    private BigDecimal realSalary;

    /**
     * 13、单位社保(手动)
     */
    @Column(name = "`company_social_security`")
    private BigDecimal companySocialSecurity;

    /**
     * 14、单位公积金(手动)
     */
    @Column(name = "`company_house_fund`")
    private BigDecimal companyHouseFund;

}

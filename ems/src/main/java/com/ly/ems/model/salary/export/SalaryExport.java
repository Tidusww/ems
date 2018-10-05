package com.ly.ems.model.salary.export;

import com.ly.ems.common.utils.file.ExcelAttribute;

import java.math.BigDecimal;

/**
 * Created by tidus on 2018/10/3.
 */
public class SalaryExport {
    /**
     * 标题定义
     */
    @ExcelAttribute(content ="员工工资表（本地/外地/劳务派遣/外包作业）", isTitle = true, titleRegion = "0,0,A,Z")
    private String title;
    /**
     * 标题定义
     */
    @ExcelAttribute(paramKey ="month", isTitle = true, titleRegion = "1,1,F,S")
    private String month;


    /**
     * 员工id
     */
    @ExcelAttribute(content = "员工姓名", column = "A")
    private String employeeName;

    /**
     * 1、最低月基本工资
     */
    @ExcelAttribute(content = "月基本工资", column = "B")
    private BigDecimal basicSalary;

    /**
     * 2、加班工资：工种工资*（总出勤天数-21.75）*2
     */
    @ExcelAttribute(content = "加班工资", column = "C")
    private BigDecimal overtimeSalary;

    /**
     * 3、计量工资：工种工资*出勤天数-基本工资-加班工资
     */
    @ExcelAttribute(content = "计量工资", column = "D")
    private BigDecimal calculateSalary;

    /**
     * 4、高温费：总出勤天数*每日高温费
     */
    @ExcelAttribute(content = "高温费", column = "E")
    private BigDecimal hotAllowance;

    /**
     * 5、社保补贴：30*每日社保
     */
    @ExcelAttribute(content = "社保补贴", column = "F")
    private BigDecimal socialSecurityAllowance;

    /**
     * 6、住房补贴：30*每日住房
     */
    @ExcelAttribute(content = "住房补贴", column = "G")
    private BigDecimal houseFundAllowance;

    /**
     * 7、其他收入（手动）
     */
    @ExcelAttribute(content = "其他收入", column = "H")
    private BigDecimal otherIncome;

    /**
     * 8、应付工资（含税工资）：【1】+【2】+【3】+【4】+【5】+【6】+【7】
     */
    @ExcelAttribute(content = "应付工资", column = "I")
    private BigDecimal payableSalary;

    /**
     * 9、个人部分社保(手动)
     */
    @ExcelAttribute(content = "个人部分社保", column = "J")
    private BigDecimal personalSocialSecurity;

    /**
     * 10、个人部分公积金(手动)
     */
    @ExcelAttribute(content = "个人部分公积金", column = "K")
    private BigDecimal personalHouseFund;

    /**
     * 11、其他扣除：借支或扣费（手动）
     */
    @ExcelAttribute(content = "其他扣除", column = "L")
    private BigDecimal otherDeduction;

    /**
     * 应付个税：最新税法
     */
    @ExcelAttribute(content = "应付个税", column = "M")
    private BigDecimal payTaxes;

    /**
     * 12、实发工资：【8】-【9】-【10】-【11】-【应付个税】
     */
    @ExcelAttribute(content = "实发工资", column = "N")
    private BigDecimal realSalary;

    /**
     * 13、单位社保(手动)
     */
    @ExcelAttribute(content = "单位社保", column = "O")
    private BigDecimal companySocialSecurity;

    /**
     * 14、单位公积金(手动)
     */
    @ExcelAttribute(content = "单位公积金", column = "P")
    private BigDecimal companyHouseFund;

}

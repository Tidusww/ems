package com.ly.ems.model.salary.export;

import com.ly.ems.common.utils.file.ExcelAttribute;

import java.math.BigDecimal;

/**
 * Created by tidus on 2018/10/3.
 */
@ExcelAttribute(contentRow = 6)
public class SalaryExport {


    /**
     * 固定单元数据定义
     */
    @ExcelAttribute(key = "month", isColumn = false, region = "3,G")
    private String month;
    @ExcelAttribute(key = "companyName", isColumn = false, region = "1,B")
    private String companyName;
    @ExcelAttribute(key = "projectName", isColumn = false, region = "2,B")
    private String projectName;

    /**
     * 列表数据列定义
     */
    @ExcelAttribute(content = "班组", column = "A")
    private String groupName;
    @ExcelAttribute(content = "序号", column = "B")
    private String numInGroup;
    @ExcelAttribute(content = "姓名", column = "C")
    private String employeeName;
    @ExcelAttribute(content = "身份证号", column = "D")
    private String idCard;
    @ExcelAttribute(content = "性别", column = "E")
    private String gender;
    @ExcelAttribute(content = "工作天数", column = "F")
    private String attendanceDays;

    @ExcelAttribute(content = "日工资", column = "G")
    private BigDecimal dailySalary;
    @ExcelAttribute(content = "日社保补贴", column = "H")
    private BigDecimal socialSecurityAllowance;
    @ExcelAttribute(content = "日住房补贴", column = "I")
    private BigDecimal houseFundAllowance;
    @ExcelAttribute(content = "日高温津贴", column = "J")
    private BigDecimal hotAllowance;
    @ExcelAttribute(content = "其他收入", column = "K")
    private BigDecimal otherIncome;
    @ExcelAttribute(content = "应付工资", column = "L")
    private BigDecimal payableSalary;

    @ExcelAttribute(content = "社保费", column = "M")
    private BigDecimal personalSocialSecurity;
    @ExcelAttribute(content = "住房公积", column = "N")
    private BigDecimal personalHouseFund;
    @ExcelAttribute(content = "个税", column = "O")
    private BigDecimal payTaxes;
    @ExcelAttribute(content = "其他扣除", column = "P")
    private BigDecimal otherDeduction;

    @ExcelAttribute(content = "实发工资", column = "Q")
    private BigDecimal realSalary;

    @ExcelAttribute(content = "单位社保", column = "R")
    private BigDecimal companySocialSecurity;
    @ExcelAttribute(content = "单位公积金", column = "S")
    private BigDecimal companyHouseFund;


}

package com.ly.ems.model.salary.export;

import com.ly.ems.common.utils.file.ExcelAttribute;

import java.math.BigDecimal;

/**
 * Created by tidus on 2018/10/3.
 */
public class SalaryExport {
    /**
     * 纯标题定义
     */
    @ExcelAttribute(content ="员工工资表（本地/外地/劳务派遣/外包作业）", isPureTitle = true, titleRegion = "0,0,A,Z")
    private String title;
    @ExcelAttribute(paramKey ="month", isPureTitle = true, titleRegion = "1,1,F,S")
    private String month;
    @ExcelAttribute(content ="单位：元/人民币", isPureTitle = true, titleRegion = "1,1,T,T")
    private String unit;
    @ExcelAttribute(content ="出勤情况", isPureTitle = true, titleRegion = "1,3,F,G")
    private String attendanceDetail;
    @ExcelAttribute(content ="应发项目", isPureTitle = true, titleRegion = "1,3,H,N")
    private String payableItems;
    @ExcelAttribute(content ="应扣项目", isPureTitle = true, titleRegion = "1,3,P,S")
    private String deductableItems;



    /**
     * 数据列定义
     */
    @ExcelAttribute(content = "班组", column = "A", titleRegion = "1,3,A,A")
    private String groupName;
    @ExcelAttribute(content = "序号", isPureTitle = true, column = "B", titleRegion = "1,3,B,B")
    private String numberInGroup;
    @ExcelAttribute(content = "员工姓名", column = "C", titleRegion = "1,3,C,C")
    private String employeeName;
    @ExcelAttribute(content = "身份证号", column = "D", titleRegion = "1,3,D,D")
    private String idCard;
    @ExcelAttribute(content = "性别", column = "E", titleRegion = "1,3,E,E")
    private String gender;
    @ExcelAttribute(content = "正常出勤天数", column = "F", titleRegion = "3,3,F,F")
    private String attendanceDays;
    @ExcelAttribute(content = "加班天数", column = "G", titleRegion = "3,3,G,G")
    private String overtimeDays;


    @ExcelAttribute(content = "基本工资", column = "H", titleRegion = "3,3,H,H")
    private BigDecimal basicSalary;
    @ExcelAttribute(content = "加班工资", column = "I", titleRegion = "3,3,I,I")
    private BigDecimal overtimeSalary;

    @ExcelAttribute(content = "计量工资", column = "J", titleRegion = "3,3,J,J")
    private BigDecimal calculateSalary;
    @ExcelAttribute(content = "高温费", column = "K", titleRegion = "3,3,K,K")
    private BigDecimal hotAllowance;
    @ExcelAttribute(content = "社保补贴", column = "L", titleRegion = "3,3,L,L")
    private BigDecimal socialSecurityAllowance;
    @ExcelAttribute(content = "住房补贴", column = "M", titleRegion = "3,3,M,M")
    private BigDecimal houseFundAllowance;
    @ExcelAttribute(content = "其他收入", column = "N", titleRegion = "3,3,N,N")
    private BigDecimal otherIncome;

    @ExcelAttribute(content = "应付工资", column = "O", titleRegion = "2,3,O,O")
    private BigDecimal payableSalary;

    @ExcelAttribute(content = "个人部分社保", column = "P", titleRegion = "3,3,P,P")
    private BigDecimal personalSocialSecurity;
    @ExcelAttribute(content = "个人部分公积金", column = "Q", titleRegion = "3,3,Q,Q")
    private BigDecimal personalHouseFund;
    @ExcelAttribute(content = "其他扣除", column = "R", titleRegion = "3,3,R,R")
    private BigDecimal otherDeduction;
    @ExcelAttribute(content = "应付个税", column = "S", titleRegion = "3,3,S,S")
    private BigDecimal payTaxes;

    @ExcelAttribute(content = "实发工资", column = "T", titleRegion = "2,3,T,T")
    private BigDecimal realSalary;

    @ExcelAttribute(content ="日工资", column = "U", titleRegion = "1,3,U,U")
    private String dailySalary;
    @ExcelAttribute(content = "单位社保", column = "V", titleRegion = "1,3,V,V")
    private BigDecimal companySocialSecurity;
    @ExcelAttribute(content = "单位公积金", column = "W", titleRegion = "1,3,W,W")
    private BigDecimal companyHouseFund;



}

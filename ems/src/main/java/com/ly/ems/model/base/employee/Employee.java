package com.ly.ems.model.base.employee;

import com.ly.ems.core.mybatis.pagehelper.PageableModel;
import com.ly.ems.model.base.employee.constant.GenderEnum;
import com.ly.ems.model.base.employee.constant.SalaryBankEnum;
import java.util.Date;

/**
 * Created by tidus on 2017/11/19.
 */
public class Employee extends PageableModel {


    private String employeeName;
    private GenderEnum gender;
    private String idCard;
    private String address;
    private String phone;


    private Integer groupId;
    private String groupName;
    private Integer jobId;
    private String jobName;

    //工资银行
    private SalaryBankEnum salaryBank;
    //工资账户
    private String salaryAccount;
    //社保号
    private String socialSecurityNo;
    //个人社保金额
    private String personalSsAmount;
    //公司社保金额
    private String companySsAmount;
    //公积金金额
    private String houseFundAmount;
    //入职时间
    private Date entryDate;
    //合同号
    private String contractNo;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public SalaryBankEnum getSalaryBank() {
        return salaryBank;
    }

    public void setSalaryBank(SalaryBankEnum salaryBank) {
        this.salaryBank = salaryBank;
    }

    public String getSalaryAccount() {
        return salaryAccount;
    }

    public void setSalaryAccount(String salaryAccount) {
        this.salaryAccount = salaryAccount;
    }

    public String getSocialSecurityNo() {
        return socialSecurityNo;
    }

    public void setSocialSecurityNo(String socialSecurityNo) {
        this.socialSecurityNo = socialSecurityNo;
    }

    public String getPersonalSsAmount() {
        return personalSsAmount;
    }

    public void setPersonalSsAmount(String personalSsAmount) {
        this.personalSsAmount = personalSsAmount;
    }

    public String getCompanySsAmount() {
        return companySsAmount;
    }

    public void setCompanySsAmount(String companySsAmount) {
        this.companySsAmount = companySsAmount;
    }

    public String getHouseFundAmount() {
        return houseFundAmount;
    }

    public void setHouseFundAmount(String houseFundAmount) {
        this.houseFundAmount = houseFundAmount;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}

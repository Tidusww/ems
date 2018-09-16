package com.ly.ems.model.base.employee;

import com.ly.ems.model.base.employee.constant.GenderEnum;
import com.ly.ems.model.base.employee.constant.SalaryBankEnum;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.model.common.constant.StatusEnum;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`ly_employee`")
public class Employee {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 员工姓名
     */
    @Column(name = "`employee_name`")
    private String employeeName;

    /**
     * 性别
     */
    @Column(name = "`gender`")
    private GenderEnum gender;

    /**
     * 身份证号
     */
    @Column(name = "`id_card`")
    private String idCard;

    /**
     * 住址
     */
    @Column(name = "`address`")
    private String address;

    /**
     * 员工电话
     */
    @Column(name = "`phone`")
    private String phone;

    /**
     * 班组id
     */
    @Column(name = "`group_id`")
    private Integer groupId;

    /**
     * 工种id
     */
    @Column(name = "`job_id`")
    private Integer jobId;

    /**
     * 工资银行
     */
    @Column(name = "`salary_bank`")
    private SalaryBankEnum salaryBank;

    /**
     * 工资银行账户
     */
    @Column(name = "`salary_account`")
    private String salaryAccount;

    /**
     * 社保号
     */
    @Column(name = "`social_security_no`")
    private String socialSecurityNo;

    /**
     * 个人社保金额
     */
    @Column(name = "`personal_ss_amount`")
    private BigDecimal personalSsAmount;

    /**
     * 单位社保金额
     */
    @Column(name = "`company_ss_amount`")
    private BigDecimal companySsAmount;

    /**
     * 住房公积金
     */
    @Column(name = "`house_fund_amount`")
    private BigDecimal houseFundAmount;

    /**
     * 入职时间
     */
    @Column(name = "`entry_date`")
    private Date entryDate;

    /**
     * 合同号
     */
    @Column(name = "`contract_no`")
    private String contractNo;

    /**
     * 状态, 0：未知，1：有效， 2：无效
     */
    @Column(name = "`status`")
    private StatusEnum status;

    /**
     * 启用状态, 0：禁用，1：启用
     */
    @Column(name = "`enable`")
    private EnableEnum enable;

    /**
     * 创建时间
     */
    @Column(name = "`create_date`")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @Column(name = "`update_date`")
    private Date updateDate;

    /**
     * 更新人id
     */
    @Column(name = "`update_user_id`")
    private Integer updateUserId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取员工姓名
     *
     * @return employee_name - 员工姓名
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * 设置员工姓名
     *
     * @param employeeName 员工姓名
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * 获取性别
     *
     * @return gender - 性别
     */
    public GenderEnum getGender() {
        return gender;
    }

    /**
     * 设置性别
     *
     * @param gender 性别
     */
    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    /**
     * 获取身份证号
     *
     * @return id_card - 身份证号
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证号
     *
     * @param idCard 身份证号
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取住址
     *
     * @return address - 住址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置住址
     *
     * @param address 住址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取员工电话
     *
     * @return phone - 员工电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置员工电话
     *
     * @param phone 员工电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取班组id
     *
     * @return group_id - 班组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置班组id
     *
     * @param groupId 班组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取工种id
     *
     * @return job_id - 工种id
     */
    public Integer getJobId() {
        return jobId;
    }

    /**
     * 设置工种id
     *
     * @param jobId 工种id
     */
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    /**
     * 获取工资银行
     *
     * @return salary_bank - 工资银行
     */
    public SalaryBankEnum getSalaryBank() {
        return salaryBank;
    }

    /**
     * 设置工资银行
     *
     * @param salaryBank 工资银行
     */
    public void setSalaryBank(SalaryBankEnum salaryBank) {
        this.salaryBank = salaryBank;
    }

    /**
     * 获取工资银行账户
     *
     * @return salary_account - 工资银行账户
     */
    public String getSalaryAccount() {
        return salaryAccount;
    }

    /**
     * 设置工资银行账户
     *
     * @param salaryAccount 工资银行账户
     */
    public void setSalaryAccount(String salaryAccount) {
        this.salaryAccount = salaryAccount;
    }

    /**
     * 获取社保号
     *
     * @return social_security_no - 社保号
     */
    public String getSocialSecurityNo() {
        return socialSecurityNo;
    }

    /**
     * 设置社保号
     *
     * @param socialSecurityNo 社保号
     */
    public void setSocialSecurityNo(String socialSecurityNo) {
        this.socialSecurityNo = socialSecurityNo;
    }

    /**
     * 获取个人社保金额
     *
     * @return personal_ss_amount - 个人社保金额
     */
    public BigDecimal getPersonalSsAmount() {
        return personalSsAmount;
    }

    /**
     * 设置个人社保金额
     *
     * @param personalSsAmount 个人社保金额
     */
    public void setPersonalSsAmount(BigDecimal personalSsAmount) {
        this.personalSsAmount = personalSsAmount;
    }

    /**
     * 获取单位社保金额
     *
     * @return company_ss_amount - 单位社保金额
     */
    public BigDecimal getCompanySsAmount() {
        return companySsAmount;
    }

    /**
     * 设置单位社保金额
     *
     * @param companySsAmount 单位社保金额
     */
    public void setCompanySsAmount(BigDecimal companySsAmount) {
        this.companySsAmount = companySsAmount;
    }

    /**
     * 获取住房公积金
     *
     * @return house_fund_amount - 住房公积金
     */
    public BigDecimal getHouseFundAmount() {
        return houseFundAmount;
    }

    /**
     * 设置住房公积金
     *
     * @param houseFundAmount 住房公积金
     */
    public void setHouseFundAmount(BigDecimal houseFundAmount) {
        this.houseFundAmount = houseFundAmount;
    }

    /**
     * 获取入职时间
     *
     * @return entry_date - 入职时间
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     * 设置入职时间
     *
     * @param entryDate 入职时间
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * 获取合同号
     *
     * @return contract_no - 合同号
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置合同号
     *
     * @param contractNo 合同号
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * 获取状态, 0：未知，1：有效， 2：无效
     *
     * @return status - 状态, 0：未知，1：有效， 2：无效
     */
    public StatusEnum getStatus() {
        return status;
    }

    /**
     * 设置状态, 0：未知，1：有效， 2：无效
     *
     * @param status 状态, 0：未知，1：有效， 2：无效
     */
    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    /**
     * 获取启用状态, 0：禁用，1：启用
     *
     * @return enable - 启用状态, 0：禁用，1：启用
     */
    public EnableEnum getEnable() {
        return enable;
    }

    /**
     * 设置启用状态, 0：禁用，1：启用
     *
     * @param enable 启用状态, 0：禁用，1：启用
     */
    public void setEnable(EnableEnum enable) {
        this.enable = enable;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取最后更新时间
     *
     * @return update_date - 最后更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置最后更新时间
     *
     * @param updateDate 最后更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取更新人id
     *
     * @return update_user_id - 更新人id
     */
    public Integer getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人id
     *
     * @param updateUserId 更新人id
     */
    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}
package com.ly.ems.model.salary;

import com.ly.ems.model.common.constant.EnableEnum;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`ly_salary_template`")
public class Salary {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 员工id
     */
    @Column(name = "`employee_id`")
    private Integer employeeId;

    /**
     * 1、最低月基本工资
     */
    @Column(name = "`base_salary`")
    private BigDecimal baseSalary;

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
     * 获取员工id
     *
     * @return employee_id - 员工id
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * 设置员工id
     *
     * @param employeeId 员工id
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 获取1、最低月基本工资
     *
     * @return base_salary - 1、最低月基本工资
     */
    public BigDecimal getBaseSalary() {
        return baseSalary;
    }

    /**
     * 设置1、最低月基本工资
     *
     * @param baseSalary 1、最低月基本工资
     */
    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
    }

    /**
     * 获取2、加班工资：工种工资*（总出勤天数-21.75）*2
     *
     * @return overtime_salary - 2、加班工资：工种工资*（总出勤天数-21.75）*2
     */
    public BigDecimal getOvertimeSalary() {
        return overtimeSalary;
    }

    /**
     * 设置2、加班工资：工种工资*（总出勤天数-21.75）*2
     *
     * @param overtimeSalary 2、加班工资：工种工资*（总出勤天数-21.75）*2
     */
    public void setOvertimeSalary(BigDecimal overtimeSalary) {
        this.overtimeSalary = overtimeSalary;
    }

    /**
     * 获取3、计量工资：工种工资*出勤天数-基本工资-加班工资
     *
     * @return calculate_salary - 3、计量工资：工种工资*出勤天数-基本工资-加班工资
     */
    public BigDecimal getCalculateSalary() {
        return calculateSalary;
    }

    /**
     * 设置3、计量工资：工种工资*出勤天数-基本工资-加班工资
     *
     * @param calculateSalary 3、计量工资：工种工资*出勤天数-基本工资-加班工资
     */
    public void setCalculateSalary(BigDecimal calculateSalary) {
        this.calculateSalary = calculateSalary;
    }

    /**
     * 获取4、高温费：总出勤天数*每日高温费
     *
     * @return hot_allowance - 4、高温费：总出勤天数*每日高温费
     */
    public BigDecimal getHotAllowance() {
        return hotAllowance;
    }

    /**
     * 设置4、高温费：总出勤天数*每日高温费
     *
     * @param hotAllowance 4、高温费：总出勤天数*每日高温费
     */
    public void setHotAllowance(BigDecimal hotAllowance) {
        this.hotAllowance = hotAllowance;
    }

    /**
     * 获取5、社保补贴：30*每日社保
     *
     * @return social_security_allowance - 5、社保补贴：30*每日社保
     */
    public BigDecimal getSocialSecurityAllowance() {
        return socialSecurityAllowance;
    }

    /**
     * 设置5、社保补贴：30*每日社保
     *
     * @param socialSecurityAllowance 5、社保补贴：30*每日社保
     */
    public void setSocialSecurityAllowance(BigDecimal socialSecurityAllowance) {
        this.socialSecurityAllowance = socialSecurityAllowance;
    }

    /**
     * 获取6、住房补贴：30*每日住房
     *
     * @return house_fund_allowance - 6、住房补贴：30*每日住房
     */
    public BigDecimal getHouseFundAllowance() {
        return houseFundAllowance;
    }

    /**
     * 设置6、住房补贴：30*每日住房
     *
     * @param houseFundAllowance 6、住房补贴：30*每日住房
     */
    public void setHouseFundAllowance(BigDecimal houseFundAllowance) {
        this.houseFundAllowance = houseFundAllowance;
    }

    /**
     * 获取7、其他收入（手动）
     *
     * @return other_income - 7、其他收入（手动）
     */
    public BigDecimal getOtherIncome() {
        return otherIncome;
    }

    /**
     * 设置7、其他收入（手动）
     *
     * @param otherIncome 7、其他收入（手动）
     */
    public void setOtherIncome(BigDecimal otherIncome) {
        this.otherIncome = otherIncome;
    }

    /**
     * 获取8、应付工资（含税工资）：【1】+【2】+【3】+【4】+【5】+【6】+【7】
     *
     * @return payable_salary - 8、应付工资（含税工资）：【1】+【2】+【3】+【4】+【5】+【6】+【7】
     */
    public BigDecimal getPayableSalary() {
        return payableSalary;
    }

    /**
     * 设置8、应付工资（含税工资）：【1】+【2】+【3】+【4】+【5】+【6】+【7】
     *
     * @param payableSalary 8、应付工资（含税工资）：【1】+【2】+【3】+【4】+【5】+【6】+【7】
     */
    public void setPayableSalary(BigDecimal payableSalary) {
        this.payableSalary = payableSalary;
    }

    /**
     * 获取9、个人部分社保(手动)
     *
     * @return personal_social_security - 9、个人部分社保(手动)
     */
    public BigDecimal getPersonalSocialSecurity() {
        return personalSocialSecurity;
    }

    /**
     * 设置9、个人部分社保(手动)
     *
     * @param personalSocialSecurity 9、个人部分社保(手动)
     */
    public void setPersonalSocialSecurity(BigDecimal personalSocialSecurity) {
        this.personalSocialSecurity = personalSocialSecurity;
    }

    /**
     * 获取10、个人部分公积金(手动)
     *
     * @return personal_house_fund - 10、个人部分公积金(手动)
     */
    public BigDecimal getPersonalHouseFund() {
        return personalHouseFund;
    }

    /**
     * 设置10、个人部分公积金(手动)
     *
     * @param personalHouseFund 10、个人部分公积金(手动)
     */
    public void setPersonalHouseFund(BigDecimal personalHouseFund) {
        this.personalHouseFund = personalHouseFund;
    }

    /**
     * 获取11、其他扣除：借支或扣费（手动）
     *
     * @return other_deduction - 11、其他扣除：借支或扣费（手动）
     */
    public BigDecimal getOtherDeduction() {
        return otherDeduction;
    }

    /**
     * 设置11、其他扣除：借支或扣费（手动）
     *
     * @param otherDeduction 11、其他扣除：借支或扣费（手动）
     */
    public void setOtherDeduction(BigDecimal otherDeduction) {
        this.otherDeduction = otherDeduction;
    }

    /**
     * 获取应付个税：最新税法
     *
     * @return pay_taxes - 应付个税：最新税法
     */
    public BigDecimal getPayTaxes() {
        return payTaxes;
    }

    /**
     * 设置应付个税：最新税法
     *
     * @param payTaxes 应付个税：最新税法
     */
    public void setPayTaxes(BigDecimal payTaxes) {
        this.payTaxes = payTaxes;
    }

    /**
     * 获取12、实发工资：【8】-【9】-【10】-【11】-【应付个税】
     *
     * @return real_salary - 12、实发工资：【8】-【9】-【10】-【11】-【应付个税】
     */
    public BigDecimal getRealSalary() {
        return realSalary;
    }

    /**
     * 设置12、实发工资：【8】-【9】-【10】-【11】-【应付个税】
     *
     * @param realSalary 12、实发工资：【8】-【9】-【10】-【11】-【应付个税】
     */
    public void setRealSalary(BigDecimal realSalary) {
        this.realSalary = realSalary;
    }

    /**
     * 获取13、单位社保(手动)
     *
     * @return company_social_security - 13、单位社保(手动)
     */
    public BigDecimal getCompanySocialSecurity() {
        return companySocialSecurity;
    }

    /**
     * 设置13、单位社保(手动)
     *
     * @param companySocialSecurity 13、单位社保(手动)
     */
    public void setCompanySocialSecurity(BigDecimal companySocialSecurity) {
        this.companySocialSecurity = companySocialSecurity;
    }

    /**
     * 获取14、单位公积金(手动)
     *
     * @return company_house_fund - 14、单位公积金(手动)
     */
    public BigDecimal getCompanyHouseFund() {
        return companyHouseFund;
    }

    /**
     * 设置14、单位公积金(手动)
     *
     * @param companyHouseFund 14、单位公积金(手动)
     */
    public void setCompanyHouseFund(BigDecimal companyHouseFund) {
        this.companyHouseFund = companyHouseFund;
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
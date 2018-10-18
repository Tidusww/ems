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
     * 出勤天数
     */
    @Column(name = "`attendance_days`")
    private Integer attendanceDays;

    /**
     * 1.日工资=工种的工资标准
     */
    @Column(name = "`job_salary`")
    private BigDecimal jobSalary;

    /**
     * 2.日社保补贴
     */
    @Column(name = "`social_security_allowance`")
    private BigDecimal socialSecurityAllowance;

    /**
     * 3.日住房补贴
     */
    @Column(name = "`house_fund_allowance`")
    private BigDecimal houseFundAllowance;

    /**
     * 4.日高温津贴
     */
    @Column(name = "`hot_allowance`")
    private BigDecimal hotAllowance;

    /**
     * 5、其他收入（手动）
     */
    @Column(name = "`other_income`")
    private BigDecimal otherIncome;

    /**
     * 6、应付工资（含税工资）：（【1】+【2】+【3】+【4】）* 出勤天数 +【5】
     */
    @Column(name = "`payable_salary`")
    private BigDecimal payableSalary;

    /**
     * 7、个人部分社保(手动)
     */
    @Column(name = "`personal_social_security`")
    private BigDecimal personalSocialSecurity;

    /**
     * 8、个人部分公积金(手动)
     */
    @Column(name = "`personal_house_fund`")
    private BigDecimal personalHouseFund;

    /**
     * 9、应付个税
     */
    @Column(name = "`pay_taxes`")
    private BigDecimal payTaxes;

    /**
     * 10、其他扣除：借支或扣费（手动）
     */
    @Column(name = "`other_deduction`")
    private BigDecimal otherDeduction;

    /**
     * 11、实发工资：【6】-【7】-【8】-【9】-【10】
     */
    @Column(name = "`real_salary`")
    private BigDecimal realSalary;

    /**
     * 12、单位社保(手动)
     */
    @Column(name = "`company_social_security`")
    private BigDecimal companySocialSecurity;

    /**
     * 13、单位公积金(手动)
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
     * 获取出勤天数
     *
     * @return attendance_days - 出勤天数
     */
    public Integer getAttendanceDays() {
        return attendanceDays;
    }

    /**
     * 设置出勤天数
     *
     * @param attendanceDays 出勤天数
     */
    public void setAttendanceDays(Integer attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    /**
     * 获取1.日工资=工种的工资标准
     *
     * @return job_salary - 1.日工资=工种的工资标准
     */
    public BigDecimal getJobSalary() {
        return jobSalary;
    }

    /**
     * 设置1.日工资=工种的工资标准
     *
     * @param jobSalary 1.日工资=工种的工资标准
     */
    public void setJobSalary(BigDecimal jobSalary) {
        this.jobSalary = jobSalary;
    }

    /**
     * 获取2.日社保补贴
     *
     * @return social_security_allowance - 2.日社保补贴
     */
    public BigDecimal getSocialSecurityAllowance() {
        return socialSecurityAllowance;
    }

    /**
     * 设置2.日社保补贴
     *
     * @param socialSecurityAllowance 2.日社保补贴
     */
    public void setSocialSecurityAllowance(BigDecimal socialSecurityAllowance) {
        this.socialSecurityAllowance = socialSecurityAllowance;
    }

    /**
     * 获取3.日住房补贴
     *
     * @return house_fund_allowance - 3.日住房补贴
     */
    public BigDecimal getHouseFundAllowance() {
        return houseFundAllowance;
    }

    /**
     * 设置3.日住房补贴
     *
     * @param houseFundAllowance 3.日住房补贴
     */
    public void setHouseFundAllowance(BigDecimal houseFundAllowance) {
        this.houseFundAllowance = houseFundAllowance;
    }

    /**
     * 获取4.日高温津贴
     *
     * @return hot_allowance - 4.日高温津贴
     */
    public BigDecimal getHotAllowance() {
        return hotAllowance;
    }

    /**
     * 设置4.日高温津贴
     *
     * @param hotAllowance 4.日高温津贴
     */
    public void setHotAllowance(BigDecimal hotAllowance) {
        this.hotAllowance = hotAllowance;
    }

    /**
     * 获取5、其他收入（手动）
     *
     * @return other_income - 5、其他收入（手动）
     */
    public BigDecimal getOtherIncome() {
        return otherIncome;
    }

    /**
     * 设置5、其他收入（手动）
     *
     * @param otherIncome 5、其他收入（手动）
     */
    public void setOtherIncome(BigDecimal otherIncome) {
        this.otherIncome = otherIncome;
    }

    /**
     * 获取6、应付工资（含税工资）：（【1】+【2】+【3】+【4】）* 出勤天数 +【5】
     *
     * @return payable_salary - 6、应付工资（含税工资）：（【1】+【2】+【3】+【4】）* 出勤天数 +【5】
     */
    public BigDecimal getPayableSalary() {
        return payableSalary;
    }

    /**
     * 设置6、应付工资（含税工资）：（【1】+【2】+【3】+【4】）* 出勤天数 +【5】
     *
     * @param payableSalary 6、应付工资（含税工资）：（【1】+【2】+【3】+【4】）* 出勤天数 +【5】
     */
    public void setPayableSalary(BigDecimal payableSalary) {
        this.payableSalary = payableSalary;
    }

    /**
     * 获取7、个人部分社保(手动)
     *
     * @return personal_social_security - 7、个人部分社保(手动)
     */
    public BigDecimal getPersonalSocialSecurity() {
        return personalSocialSecurity;
    }

    /**
     * 设置7、个人部分社保(手动)
     *
     * @param personalSocialSecurity 7、个人部分社保(手动)
     */
    public void setPersonalSocialSecurity(BigDecimal personalSocialSecurity) {
        this.personalSocialSecurity = personalSocialSecurity;
    }

    /**
     * 获取8、个人部分公积金(手动)
     *
     * @return personal_house_fund - 8、个人部分公积金(手动)
     */
    public BigDecimal getPersonalHouseFund() {
        return personalHouseFund;
    }

    /**
     * 设置8、个人部分公积金(手动)
     *
     * @param personalHouseFund 8、个人部分公积金(手动)
     */
    public void setPersonalHouseFund(BigDecimal personalHouseFund) {
        this.personalHouseFund = personalHouseFund;
    }

    /**
     * 获取9、应付个税
     *
     * @return pay_taxes - 9、应付个税
     */
    public BigDecimal getPayTaxes() {
        return payTaxes;
    }

    /**
     * 设置9、应付个税
     *
     * @param payTaxes 9、应付个税
     */
    public void setPayTaxes(BigDecimal payTaxes) {
        this.payTaxes = payTaxes;
    }

    /**
     * 获取10、其他扣除：借支或扣费（手动）
     *
     * @return other_deduction - 10、其他扣除：借支或扣费（手动）
     */
    public BigDecimal getOtherDeduction() {
        return otherDeduction;
    }

    /**
     * 设置10、其他扣除：借支或扣费（手动）
     *
     * @param otherDeduction 10、其他扣除：借支或扣费（手动）
     */
    public void setOtherDeduction(BigDecimal otherDeduction) {
        this.otherDeduction = otherDeduction;
    }

    /**
     * 获取11、实发工资：【6】-【7】-【8】-【9】-【10】
     *
     * @return real_salary - 11、实发工资：【6】-【7】-【8】-【9】-【10】
     */
    public BigDecimal getRealSalary() {
        return realSalary;
    }

    /**
     * 设置11、实发工资：【6】-【7】-【8】-【9】-【10】
     *
     * @param realSalary 11、实发工资：【6】-【7】-【8】-【9】-【10】
     */
    public void setRealSalary(BigDecimal realSalary) {
        this.realSalary = realSalary;
    }

    /**
     * 获取12、单位社保(手动)
     *
     * @return company_social_security - 12、单位社保(手动)
     */
    public BigDecimal getCompanySocialSecurity() {
        return companySocialSecurity;
    }

    /**
     * 设置12、单位社保(手动)
     *
     * @param companySocialSecurity 12、单位社保(手动)
     */
    public void setCompanySocialSecurity(BigDecimal companySocialSecurity) {
        this.companySocialSecurity = companySocialSecurity;
    }

    /**
     * 获取13、单位公积金(手动)
     *
     * @return company_house_fund - 13、单位公积金(手动)
     */
    public BigDecimal getCompanyHouseFund() {
        return companyHouseFund;
    }

    /**
     * 设置13、单位公积金(手动)
     *
     * @param companyHouseFund 13、单位公积金(手动)
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
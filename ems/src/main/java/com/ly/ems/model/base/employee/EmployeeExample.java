package com.ly.ems.model.base.employee;

import com.ly.ems.model.base.employee.constant.EmployeeStatusEnum;
import com.ly.ems.model.base.employee.constant.GenderEnum;
import com.ly.ems.model.base.employee.constant.SalaryBankEnum;
import com.ly.ems.model.common.constant.EnableEnum;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EmployeeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIsNull() {
            addCriterion("employee_name is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIsNotNull() {
            addCriterion("employee_name is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameEqualTo(String value) {
            addCriterion("employee_name =", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotEqualTo(String value) {
            addCriterion("employee_name <>", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameGreaterThan(String value) {
            addCriterion("employee_name >", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameGreaterThanOrEqualTo(String value) {
            addCriterion("employee_name >=", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLessThan(String value) {
            addCriterion("employee_name <", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLessThanOrEqualTo(String value) {
            addCriterion("employee_name <=", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLike(String value) {
            addCriterion("employee_name like", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotLike(String value) {
            addCriterion("employee_name not like", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIn(List<String> values) {
            addCriterion("employee_name in", values, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotIn(List<String> values) {
            addCriterion("employee_name not in", values, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameBetween(String value1, String value2) {
            addCriterion("employee_name between", value1, value2, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotBetween(String value1, String value2) {
            addCriterion("employee_name not between", value1, value2, "employeeName");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(GenderEnum value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(GenderEnum value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(GenderEnum value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(GenderEnum value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(GenderEnum value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(GenderEnum value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<GenderEnum> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<GenderEnum> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(GenderEnum value1, GenderEnum value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(GenderEnum value1, GenderEnum value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNull() {
            addCriterion("id_card is null");
            return (Criteria) this;
        }

        public Criteria andIdCardIsNotNull() {
            addCriterion("id_card is not null");
            return (Criteria) this;
        }

        public Criteria andIdCardEqualTo(String value) {
            addCriterion("id_card =", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotEqualTo(String value) {
            addCriterion("id_card <>", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThan(String value) {
            addCriterion("id_card >", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardGreaterThanOrEqualTo(String value) {
            addCriterion("id_card >=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThan(String value) {
            addCriterion("id_card <", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLessThanOrEqualTo(String value) {
            addCriterion("id_card <=", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardLike(String value) {
            addCriterion("id_card like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotLike(String value) {
            addCriterion("id_card not like", value, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardIn(List<String> values) {
            addCriterion("id_card in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotIn(List<String> values) {
            addCriterion("id_card not in", values, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardBetween(String value1, String value2) {
            addCriterion("id_card between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andIdCardNotBetween(String value1, String value2) {
            addCriterion("id_card not between", value1, value2, "idCard");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(Integer value) {
            addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(Integer value) {
            addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(Integer value) {
            addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(Integer value) {
            addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(Integer value) {
            addCriterion("group_id <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<Integer> values) {
            addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<Integer> values) {
            addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(Integer value1, Integer value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(Integer value1, Integer value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andJobIdIsNull() {
            addCriterion("job_id is null");
            return (Criteria) this;
        }

        public Criteria andJobIdIsNotNull() {
            addCriterion("job_id is not null");
            return (Criteria) this;
        }

        public Criteria andJobIdEqualTo(Integer value) {
            addCriterion("job_id =", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotEqualTo(Integer value) {
            addCriterion("job_id <>", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdGreaterThan(Integer value) {
            addCriterion("job_id >", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("job_id >=", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdLessThan(Integer value) {
            addCriterion("job_id <", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdLessThanOrEqualTo(Integer value) {
            addCriterion("job_id <=", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdIn(List<Integer> values) {
            addCriterion("job_id in", values, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotIn(List<Integer> values) {
            addCriterion("job_id not in", values, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdBetween(Integer value1, Integer value2) {
            addCriterion("job_id between", value1, value2, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotBetween(Integer value1, Integer value2) {
            addCriterion("job_id not between", value1, value2, "jobId");
            return (Criteria) this;
        }

        public Criteria andSalaryBankIsNull() {
            addCriterion("salary_bank is null");
            return (Criteria) this;
        }

        public Criteria andSalaryBankIsNotNull() {
            addCriterion("salary_bank is not null");
            return (Criteria) this;
        }

        public Criteria andSalaryBankEqualTo(SalaryBankEnum value) {
            addCriterion("salary_bank =", value, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankNotEqualTo(SalaryBankEnum value) {
            addCriterion("salary_bank <>", value, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankGreaterThan(SalaryBankEnum value) {
            addCriterion("salary_bank >", value, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankGreaterThanOrEqualTo(SalaryBankEnum value) {
            addCriterion("salary_bank >=", value, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankLessThan(SalaryBankEnum value) {
            addCriterion("salary_bank <", value, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankLessThanOrEqualTo(SalaryBankEnum value) {
            addCriterion("salary_bank <=", value, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankLike(SalaryBankEnum value) {
            addCriterion("salary_bank like", value, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankNotLike(SalaryBankEnum value) {
            addCriterion("salary_bank not like", value, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankIn(List<SalaryBankEnum> values) {
            addCriterion("salary_bank in", values, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankNotIn(List<SalaryBankEnum> values) {
            addCriterion("salary_bank not in", values, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankBetween(SalaryBankEnum value1, SalaryBankEnum value2) {
            addCriterion("salary_bank between", value1, value2, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryBankNotBetween(SalaryBankEnum value1, SalaryBankEnum value2) {
            addCriterion("salary_bank not between", value1, value2, "salaryBank");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountIsNull() {
            addCriterion("salary_account is null");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountIsNotNull() {
            addCriterion("salary_account is not null");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountEqualTo(String value) {
            addCriterion("salary_account =", value, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountNotEqualTo(String value) {
            addCriterion("salary_account <>", value, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountGreaterThan(String value) {
            addCriterion("salary_account >", value, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountGreaterThanOrEqualTo(String value) {
            addCriterion("salary_account >=", value, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountLessThan(String value) {
            addCriterion("salary_account <", value, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountLessThanOrEqualTo(String value) {
            addCriterion("salary_account <=", value, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountLike(String value) {
            addCriterion("salary_account like", value, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountNotLike(String value) {
            addCriterion("salary_account not like", value, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountIn(List<String> values) {
            addCriterion("salary_account in", values, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountNotIn(List<String> values) {
            addCriterion("salary_account not in", values, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountBetween(String value1, String value2) {
            addCriterion("salary_account between", value1, value2, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSalaryAccountNotBetween(String value1, String value2) {
            addCriterion("salary_account not between", value1, value2, "salaryAccount");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoIsNull() {
            addCriterion("social_security_no is null");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoIsNotNull() {
            addCriterion("social_security_no is not null");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoEqualTo(String value) {
            addCriterion("social_security_no =", value, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoNotEqualTo(String value) {
            addCriterion("social_security_no <>", value, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoGreaterThan(String value) {
            addCriterion("social_security_no >", value, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoGreaterThanOrEqualTo(String value) {
            addCriterion("social_security_no >=", value, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoLessThan(String value) {
            addCriterion("social_security_no <", value, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoLessThanOrEqualTo(String value) {
            addCriterion("social_security_no <=", value, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoLike(String value) {
            addCriterion("social_security_no like", value, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoNotLike(String value) {
            addCriterion("social_security_no not like", value, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoIn(List<String> values) {
            addCriterion("social_security_no in", values, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoNotIn(List<String> values) {
            addCriterion("social_security_no not in", values, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoBetween(String value1, String value2) {
            addCriterion("social_security_no between", value1, value2, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andSocialSecurityNoNotBetween(String value1, String value2) {
            addCriterion("social_security_no not between", value1, value2, "socialSecurityNo");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountIsNull() {
            addCriterion("personal_ss_amount is null");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountIsNotNull() {
            addCriterion("personal_ss_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountEqualTo(BigDecimal value) {
            addCriterion("personal_ss_amount =", value, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountNotEqualTo(BigDecimal value) {
            addCriterion("personal_ss_amount <>", value, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountGreaterThan(BigDecimal value) {
            addCriterion("personal_ss_amount >", value, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("personal_ss_amount >=", value, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountLessThan(BigDecimal value) {
            addCriterion("personal_ss_amount <", value, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("personal_ss_amount <=", value, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountIn(List<BigDecimal> values) {
            addCriterion("personal_ss_amount in", values, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountNotIn(List<BigDecimal> values) {
            addCriterion("personal_ss_amount not in", values, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("personal_ss_amount between", value1, value2, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andPersonalSsAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("personal_ss_amount not between", value1, value2, "personalSsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountIsNull() {
            addCriterion("company_ss_amount is null");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountIsNotNull() {
            addCriterion("company_ss_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountEqualTo(BigDecimal value) {
            addCriterion("company_ss_amount =", value, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountNotEqualTo(BigDecimal value) {
            addCriterion("company_ss_amount <>", value, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountGreaterThan(BigDecimal value) {
            addCriterion("company_ss_amount >", value, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("company_ss_amount >=", value, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountLessThan(BigDecimal value) {
            addCriterion("company_ss_amount <", value, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("company_ss_amount <=", value, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountIn(List<BigDecimal> values) {
            addCriterion("company_ss_amount in", values, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountNotIn(List<BigDecimal> values) {
            addCriterion("company_ss_amount not in", values, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("company_ss_amount between", value1, value2, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andCompanySsAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("company_ss_amount not between", value1, value2, "companySsAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountIsNull() {
            addCriterion("house_fund_amount is null");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountIsNotNull() {
            addCriterion("house_fund_amount is not null");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountEqualTo(BigDecimal value) {
            addCriterion("house_fund_amount =", value, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountNotEqualTo(BigDecimal value) {
            addCriterion("house_fund_amount <>", value, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountGreaterThan(BigDecimal value) {
            addCriterion("house_fund_amount >", value, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("house_fund_amount >=", value, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountLessThan(BigDecimal value) {
            addCriterion("house_fund_amount <", value, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("house_fund_amount <=", value, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountIn(List<BigDecimal> values) {
            addCriterion("house_fund_amount in", values, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountNotIn(List<BigDecimal> values) {
            addCriterion("house_fund_amount not in", values, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("house_fund_amount between", value1, value2, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andHouseFundAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("house_fund_amount not between", value1, value2, "houseFundAmount");
            return (Criteria) this;
        }

        public Criteria andEntryDateIsNull() {
            addCriterion("entry_date is null");
            return (Criteria) this;
        }

        public Criteria andEntryDateIsNotNull() {
            addCriterion("entry_date is not null");
            return (Criteria) this;
        }

        public Criteria andEntryDateEqualTo(Date value) {
            addCriterion("entry_date =", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateNotEqualTo(Date value) {
            addCriterion("entry_date <>", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateGreaterThan(Date value) {
            addCriterion("entry_date >", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("entry_date >=", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateLessThan(Date value) {
            addCriterion("entry_date <", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateLessThanOrEqualTo(Date value) {
            addCriterion("entry_date <=", value, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateIn(List<Date> values) {
            addCriterion("entry_date in", values, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateNotIn(List<Date> values) {
            addCriterion("entry_date not in", values, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateBetween(Date value1, Date value2) {
            addCriterion("entry_date between", value1, value2, "entryDate");
            return (Criteria) this;
        }

        public Criteria andEntryDateNotBetween(Date value1, Date value2) {
            addCriterion("entry_date not between", value1, value2, "entryDate");
            return (Criteria) this;
        }

        public Criteria andContractNoIsNull() {
            addCriterion("contract_no is null");
            return (Criteria) this;
        }

        public Criteria andContractNoIsNotNull() {
            addCriterion("contract_no is not null");
            return (Criteria) this;
        }

        public Criteria andContractNoEqualTo(String value) {
            addCriterion("contract_no =", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotEqualTo(String value) {
            addCriterion("contract_no <>", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoGreaterThan(String value) {
            addCriterion("contract_no >", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoGreaterThanOrEqualTo(String value) {
            addCriterion("contract_no >=", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLessThan(String value) {
            addCriterion("contract_no <", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLessThanOrEqualTo(String value) {
            addCriterion("contract_no <=", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLike(String value) {
            addCriterion("contract_no like", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotLike(String value) {
            addCriterion("contract_no not like", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoIn(List<String> values) {
            addCriterion("contract_no in", values, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotIn(List<String> values) {
            addCriterion("contract_no not in", values, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoBetween(String value1, String value2) {
            addCriterion("contract_no between", value1, value2, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotBetween(String value1, String value2) {
            addCriterion("contract_no not between", value1, value2, "contractNo");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(EmployeeStatusEnum value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(EmployeeStatusEnum value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(EmployeeStatusEnum value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(EmployeeStatusEnum value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(EmployeeStatusEnum value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(EmployeeStatusEnum value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<EmployeeStatusEnum> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<EmployeeStatusEnum> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(EmployeeStatusEnum value1, EmployeeStatusEnum value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(EmployeeStatusEnum value1, EmployeeStatusEnum value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andEnableIsNull() {
            addCriterion("enable is null");
            return (Criteria) this;
        }

        public Criteria andEnableIsNotNull() {
            addCriterion("enable is not null");
            return (Criteria) this;
        }

        public Criteria andEnableEqualTo(EnableEnum value) {
            addCriterion("enable =", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotEqualTo(EnableEnum value) {
            addCriterion("enable <>", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableGreaterThan(EnableEnum value) {
            addCriterion("enable >", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableGreaterThanOrEqualTo(EnableEnum value) {
            addCriterion("enable >=", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableLessThan(EnableEnum value) {
            addCriterion("enable <", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableLessThanOrEqualTo(EnableEnum value) {
            addCriterion("enable <=", value, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableIn(List<EnableEnum> values) {
            addCriterion("enable in", values, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotIn(List<EnableEnum> values) {
            addCriterion("enable not in", values, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableBetween(EnableEnum value1, EnableEnum value2) {
            addCriterion("enable between", value1, value2, "enable");
            return (Criteria) this;
        }

        public Criteria andEnableNotBetween(EnableEnum value1, EnableEnum value2) {
            addCriterion("enable not between", value1, value2, "enable");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNull() {
            addCriterion("update_user_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIsNotNull() {
            addCriterion("update_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdEqualTo(Integer value) {
            addCriterion("update_user_id =", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotEqualTo(Integer value) {
            addCriterion("update_user_id <>", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThan(Integer value) {
            addCriterion("update_user_id >", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("update_user_id >=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThan(Integer value) {
            addCriterion("update_user_id <", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("update_user_id <=", value, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdIn(List<Integer> values) {
            addCriterion("update_user_id in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotIn(List<Integer> values) {
            addCriterion("update_user_id not in", values, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id between", value1, value2, "updateUserId");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("update_user_id not between", value1, value2, "updateUserId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
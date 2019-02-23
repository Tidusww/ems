package com.ly.ems.service.employee.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.dao.base.ExtendEmployeeMapper;
import com.ly.ems.dao.base.mapper.EmployeeMapper;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeVo;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.service.employee.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);


    @Autowired
    ExtendEmployeeMapper extendEmployeeMapper;
    @Autowired
    EmployeeMapper employeeMapper;


    /**
     * 员工
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<EmployeeVo> getEmployeesByConditions(EmployeeConditions conditions) {

        List<EmployeeVo> resultList = extendEmployeeMapper.selectByConditions(conditions);
        PageInfo<EmployeeVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<EmployeeVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);
    }

    @Override
    public void saveEmployee(Employee employee) {
        // 先判断id是否重复
        if (this.checkEmployeeIdCardExist(employee.getId(), employee.getIdCard())) {
            throw new EMSRuntimeException("身份证已存在，请确认");
        }

        if (employee.getId() == null) {
            employee.setEnable(EnableEnum.ENABLED);
            employee.setCreateDate(new Date());
            employeeMapper.insertSelective(employee);
        } else {
            employeeMapper.updateByPrimaryKeySelective(employee);
        }
    }

    /**
     * 判断idCard是否已存在
     *
     * @param id
     * @param idCard
     */
    private boolean checkEmployeeIdCardExist(Integer id, String idCard) {
        Employee employee = new Employee();
        employee.setIdCard(idCard);
        employee.setEnable(EnableEnum.ENABLED);
        List<Employee> employeeList = employeeMapper.select(employee);
        // 不存在记录
        if (employeeList == null || employeeList.size() <= 0) {
            return false;
        }

        // 新增的情况
        if (id == null || id == 0 || id == -1) {
            return true;
        }

        // 修改的情况，除了自己以外存在才算重复
        for (Employee emp : employeeList) {
            if (!id.equals(emp.getId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void disableEmployee(Integer id) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setEnable(EnableEnum.DISABLED);
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public void deleteEmployee(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchInsertEmployees(List<Employee> employeeList) {
        // 与库中数据对比身份证号
        for (Employee employee : employeeList) {
            if (this.checkEmployeeIdCardExist(employee.getId(), employee.getIdCard())) {
                throw new EMSRuntimeException(String.format("导入员工失败，身份证号【%s】已存在员工库中", employee.getIdCard()));
            }
        }

        // 导入列表中查询
        for (int i = 0; i < employeeList.size(); i++) {
            for (int j = i + 1; j < employeeList.size(); j++) {
                if (employeeList.get(i).getIdCard().equals(employeeList.get(j).getIdCard())) {

                    throw new EMSRuntimeException(String.format("导入员工失败，导入列表中存在重复的身份证号【%s】", employeeList.get(i).getIdCard()));
                }
            }
        }

        for (Employee employee : employeeList) {
            employee.setCreateDate(new Date());
        }

        return extendEmployeeMapper.batchInsert(employeeList);
    }



}
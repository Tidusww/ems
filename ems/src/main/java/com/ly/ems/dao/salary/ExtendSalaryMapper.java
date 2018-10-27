package com.ly.ems.dao.salary;

import com.ly.ems.model.salary.Salary;
import com.ly.ems.model.salary.SalaryCondition;
import com.ly.ems.model.salary.SalaryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tidus on 2018/10/2.
 */
public interface ExtendSalaryMapper {
    /**
     * 条件查询(可分页)
     *
     * @param conditions
     * @param salaryTableName
     * @return
     */
    List<SalaryVo> getSalariesByConditions(@Param("conditions")SalaryCondition conditions,
                                              @Param("salaryTableName")String salaryTableName);

    /**
     * 是否存在指定月份的考勤表
     * @param salaryTableName
     * @return
     */
    int isExistSalaryTable(@Param("salaryTableName")String salaryTableName);

    /**
     * 创建指定月份的考勤表
     * @param salaryTableName
     */
    void createSalaryTable(@Param("salaryTableName")String salaryTableName);


    /**
     * 删除指定月份的考勤表
     * @param salaryTableName
     */
    void dropSalaryTable(@Param("salaryTableName")String salaryTableName);

    /**
     * 批量插入考勤信息
     * @param list
     */
    void batchInsert(@Param("salaryTableName")String salaryTableName, @Param("list") List<Salary> list);

    /**
     * 更新单条工资信息，被更新的字段为：
     *【2】~【13】
     * @param salaryTableName
     * @param salary
     * @return
     */
    int updateSalaryById(@Param("salaryTableName")String salaryTableName, @Param("salary")Salary salary);
}

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
     * 批量插入考勤信息
     * @param list
     */
    void batchInsert(@Param("salaryTableName")String salaryTableName, @Param("list") List<Salary> list);
}

package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.common.utils.file.DownloadUtil;
import com.ly.ems.common.utils.file.FileUtil;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.salary.SalaryCondition;
import com.ly.ems.model.salary.SalaryVo;
import com.ly.ems.model.salary.export.SalaryExport;
import com.ly.ems.service.salary.SalaryService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by tidus on 2018/10/2.
 */
@Controller
@RequestMapping(value = "/salary", name = "工资信息管理")
public class SalaryController {
    private Logger LOGGER = LoggerFactory.getLogger(SalaryController.class);

    @Autowired
    SalaryService salaryService;

    @ResponseBody
    @RequestMapping(value = "/get", name = "分页查询工资信息")
    public AjaxResult getSalaries(SalaryCondition conditions) {
        PageableResult<SalaryVo> pageableResult = salaryService.getSalaries(conditions);
        return AjaxResult.success(pageableResult);
    }

    /**
     * 根据所选月份的考勤情况生成工资信息
     *
     * @param conditions
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/generate", method = RequestMethod.POST, name = "生成工资信息")
    public AjaxResult generateSalaries(SalaryCondition conditions) {
        salaryService.generateSalaries(conditions);
        return AjaxResult.success("生成工资信息成功");
    }

    @RequestMapping(value = "/exportSalaries", name = "导出工资信息")
    public void exportSalaries(HttpServletRequest request, HttpServletResponse response, SalaryCondition condition) {
        // 不分页
        condition.setCurrent(0);
        condition.setPageSize(0);
        PageableResult<SalaryVo> pageableResult = salaryService.getSalaries(condition);
        List<SalaryVo> salaryVoList = pageableResult.getDataSource();

        // 自定义excel
        DownloadUtil.downloadBigExcel(SalaryExport.class, salaryVoList,
                String.format("工资表%s%s",
                        DateFormatUtils.format(condition.getMonth(), DateUtil.YYYYMM),
                        FileUtil.FILE_SUFFIX_XLSX));
    }
}

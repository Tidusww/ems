package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.common.utils.file.FileUtil;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.salary.Salary;
import com.ly.ems.model.salary.SalaryCondition;
import com.ly.ems.model.salary.SalaryVo;
import com.ly.ems.service.base.BaseInfoService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2018/10/2.
 */
@Controller
@RequestMapping(value = "/salary", name = "工资信息管理")
public class SalaryController {
    private Logger LOGGER = LoggerFactory.getLogger(SalaryController.class);

    private static final String SALARY_DETAIL_EXPORT_PREFIX = "salary_detail";
    private static final String SALARY_DETAIL_EXPORT_TEMPLATE_PATH = "/excel/template/工资明细模板表.xlsx";

    private static final String SALARY_SUMMARY_EXPORT_PREFIX = "salary_summary";
    private static final String SALARY_SUMMARY_EXPORT_TEMPLATE_PATH = "/excel/template/工资汇总模板表.xlsx";

    private static final String SALARY_DISPATCH_EXPORT_PREFIX = "salary_dispatch";
    private static final String SALARY_DISPATCH_EXPORT_TEMPLATE_PATH = "/excel/template/工资发放模板表.xlsx";

    @Autowired
    BaseInfoService baseInfoService;
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

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, name = "更新工资信息")
    public AjaxResult updateSalary(Salary salary, Date month) {
        salaryService.updateSalary(salary, month);
        return AjaxResult.success("更新工资信息成功");
    }

    /**
     * 1、导出工资明细表
     * @param request
     * @param response
     * @param condition
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exportSalaryDetail", name = "导出工资明细表")
    public AjaxResult exportSalaryDetail(HttpServletRequest request, HttpServletResponse response, SalaryCondition condition) {
        // 不分页
        condition.setCurrent(0);
        condition.setPageSize(0);
        PageableResult<SalaryVo> pageableResult = salaryService.getSalaries(condition);
        List<SalaryVo> salaryVoList = pageableResult.getDataSource();

        String excelName = String.format("工资明细表%s%s",
                DateFormatUtils.format(condition.getMonth(), DateUtil.YYYYMM),
                FileUtil.FILE_SUFFIX_XLSX);

        // 自定义excel，直接导出
//        DownloadUtil.downloadBigExcel(SalaryExport.class, salaryVoList,
//                String.format("工资表%s%s",
//                        DateFormatUtils.format(condition.getMonth(), DateUtil.YYYYMM),
//                        FileUtil.FILE_SUFFIX_XLSX));

        // jxls 导出
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("month", DateFormatUtils.format(condition.getMonth(), "yyyy年MM月"));
//        param.put("companyName", DateFormatUtils.format(condition.getMonth(), "yyyy年MM月"));
//        param.put("projectName", DateFormatUtils.format(condition.getMonth(), "yyyy年MM月"));
        param.put("itemList", salaryVoList);

        String downloadName = String.format("%s%s", SALARY_DETAIL_EXPORT_PREFIX, FileUtil.FILE_SUFFIX_XLSX);
        String path = FileUtil.generateJxlsFile(SALARY_DETAIL_EXPORT_TEMPLATE_PATH, param, downloadName);

        // 返回结果
        AjaxResult result = AjaxResult.success("生成工资信息excel成功");
        Map<String, String> fileData = new HashMap<String, String>();
        fileData.put("fileName", excelName);
        fileData.put("filePath", path);
        result.setData(fileData);
        return result;
    }

    /**
     * 2、导出工资汇总表
     * @param request
     * @param response
     * @param condition
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exportSalarySummary", name = "导出工资汇总表")
    public AjaxResult exportSalarySummary(HttpServletRequest request, HttpServletResponse response, SalaryCondition condition) {
        // 不分页
        condition.setCurrent(0);
        condition.setPageSize(0);
        PageableResult<SalaryVo> pageableResult = salaryService.getSalarySummary(condition);
        List<SalaryVo> salaryVoList = pageableResult.getDataSource();

        String excelName = String.format("工资汇总表%s%s",
                DateFormatUtils.format(condition.getMonth(), DateUtil.YYYYMM),
                FileUtil.FILE_SUFFIX_XLSX);


        // jxls 导出
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("month", DateFormatUtils.format(condition.getMonth(), "yyyy年MM月"));
        param.put("itemList", salaryVoList);

        String downloadName = String.format("%s%s", SALARY_SUMMARY_EXPORT_PREFIX, FileUtil.FILE_SUFFIX_XLSX);
        String path = FileUtil.generateJxlsFile(SALARY_SUMMARY_EXPORT_TEMPLATE_PATH, param, downloadName);

        // 返回结果
        AjaxResult result = AjaxResult.success("生成工资信息excel成功");
        Map<String, String> fileData = new HashMap<String, String>();
        fileData.put("fileName", excelName);
        fileData.put("filePath", path);
        result.setData(fileData);
        return result;
    }

    /**
     * 3、导出工资发放表
     * @param request
     * @param response
     * @param condition
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exportSalaryDispatch", name = "导出工资发放表")
    public AjaxResult exportSalaryDispatch(HttpServletRequest request, HttpServletResponse response, SalaryCondition condition) {
        // 不分页
        condition.setCurrent(0);
        condition.setPageSize(0);
        PageableResult<SalaryVo> pageableResult = salaryService.getSalaryDispatch(condition);
        List<SalaryVo> salaryVoList = pageableResult.getDataSource();

        String excelName = String.format("工资发放表%s%s",
                DateFormatUtils.format(condition.getMonth(), DateUtil.YYYYMM),
                FileUtil.FILE_SUFFIX_XLSX);

        Group group = new Group();
        if(condition.getGroupId() != null) {
            group.setId(condition.getGroupId());
            group = baseInfoService.selectOneGroup(group);
        }


        // jxls 导出
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("month", DateFormatUtils.format(condition.getMonth(), "yyyy年MM月"));
        param.put("groupName", group.getGroupName());
        param.put("itemList", salaryVoList);

        String downloadName = String.format("%s%s", SALARY_DISPATCH_EXPORT_PREFIX, FileUtil.FILE_SUFFIX_XLSX);
        String path = FileUtil.generateJxlsFile(SALARY_DISPATCH_EXPORT_TEMPLATE_PATH, param, downloadName);

        // 返回结果
        AjaxResult result = AjaxResult.success("生成工资信息excel成功");
        Map<String, String> fileData = new HashMap<String, String>();
        fileData.put("fileName", excelName);
        fileData.put("filePath", path);
        result.setData(fileData);
        return result;
    }
}

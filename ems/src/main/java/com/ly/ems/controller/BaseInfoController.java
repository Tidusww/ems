package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.common.utils.file.DownloadUtil;
import com.ly.ems.common.utils.file.ExcelUtil;
import com.ly.ems.common.utils.file.FileUtil;
import com.ly.ems.core.bean.BaseEnumConverter;
import com.ly.ems.core.bean.DateStringConverter;
import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.core.springmvc.controller.AbstractBaseController;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeVo;
import com.ly.ems.model.base.employee.constant.EmployeeStatusEnum;
import com.ly.ems.model.base.employee.constant.GenderEnum;
import com.ly.ems.model.base.employee.constant.LocationEnum;
import com.ly.ems.model.base.employee.constant.SalaryBankEnum;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.group.GroupVo;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.base.project.ProjectVo;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.service.project.ProjectService;
import com.ly.ems.service.company.CompanyService;
import com.ly.ems.service.employee.EmployeeService;
import com.ly.ems.service.group.GroupService;
import com.ly.ems.service.job.JobService;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created by tidus on 2017/11/18.
 */
@Controller
@RequestMapping(value = "/base", name = "基础信息管理")
public class BaseInfoController extends AbstractBaseController {

    private Logger LOGGER = LoggerFactory.getLogger(BaseInfoController.class);

    private static final String EMPLOYEE_IMPORT_TEMPLATE_PATH = "/excel/import/员工导入模板.xlsx";
    private static final String EMPLOYEE_IMPORT_TEMPLATE_NAME = "员工导入模板.xlsx";
    private static final String EMPLOYEE_IMPORT_XML = "/excel/import/employeeImport.xml";
    private static final List<String> EXCEL_SUPPORT_TYPES = Arrays.asList(new String[] { ".xls", ".xlsx"});
    private static final Integer EXCEL_LIMIT_SIZE = 10 * 1024 * 1024;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    GroupService groupService;

    @Autowired
    JobService jobService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ProjectService projectService;


    //region 1.员工
    /**************************************** 1.员工 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/employee/getEmployees", name = "分页查询员工")
    public AjaxResult getEmployees(EmployeeConditions conditions) {
        PageableResult<EmployeeVo> pageableResult = employeeService.getEmployeesByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/employee/save", method = RequestMethod.POST, name = "保存员工")
    public AjaxResult saveEmployee(Employee employee) {
        employeeService.saveEmployee(employee);
        return AjaxResult.success("保存员工成功");
    }
    @ResponseBody
    @RequestMapping(value = "/employee/disable", method = RequestMethod.POST, name = "作废员工")
    public AjaxResult disableEmployee(@RequestParam(name = "id") Integer id) {
        employeeService.disableEmployee(id);
        return AjaxResult.success("作废员工成功");
    }
    @ResponseBody
    @RequestMapping(value = "/employee/delete", method = RequestMethod.POST, name = "删除员工")
    public AjaxResult deleteEmployee(@RequestParam(name = "id") Integer id) {
        employeeService.deleteEmployee(id);
        return AjaxResult.success("删除员工成功");
    }

    @GetMapping(value = "/employee/template")
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            DownloadUtil.downloadFile(EMPLOYEE_IMPORT_TEMPLATE_PATH, EMPLOYEE_IMPORT_TEMPLATE_NAME);
        } catch (Exception e) {
            LOGGER.error("下载模板出错", e);
            throw new EMSRuntimeException("下载模板出错");
        }
    }
    @ResponseBody
    @PostMapping(value = "/employee/import", name = "批量导入员工")
    public AjaxResult importEmployee(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        AdminUser user = adminUserService.getCurrentUser();

        String message = FileUtil.validateFile(file, EXCEL_LIMIT_SIZE, EXCEL_SUPPORT_TYPES);
        if (StringUtils.isNotBlank(message)) {
            return AjaxResult.fail(message);
        }

        // 导入的数据
        InputStream inputXLS = file.getInputStream();

        // 导入规则
        String template = request.getSession().getServletContext().getRealPath(EMPLOYEE_IMPORT_XML);
        InputStream inputXML = new FileInputStream(template);

        // 导入时可能出现的类型装换
        Map<Class, Converter> converterClassMap = new HashMap<Class, Converter>();
        BaseEnumConverter baseEnumConverter = new BaseEnumConverter();
        converterClassMap.put(GenderEnum.class, baseEnumConverter);
        converterClassMap.put(SalaryBankEnum.class, baseEnumConverter);
        converterClassMap.put(LocationEnum.class, baseEnumConverter);
        converterClassMap.put(EmployeeStatusEnum.class, baseEnumConverter);
        converterClassMap.put(Date.class, new DateStringConverter());

        // 数据 payload
        Map<String, List<Employee>> beans = new HashMap<String, List<Employee>>();
        List<Employee> employees = new ArrayList<Employee>();
        beans.put("itemList", employees);

        if(ExcelUtil.getDataFromExcelFile(inputXLS, inputXML, beans, converterClassMap)) {
            int insertRow = employeeService.batchInsertEmployees(employees);
            return AjaxResult.success(String.format("批量导入员工成功，共导入%d条数据", insertRow));
        }else {
            return AjaxResult.fail("批量导入员工失败：读取数据失败");
        }

    }
    //endregion

    //region 2.班组
    /**************************************** 2.班组 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/group/getGroups", name = "分页查询班组")
    public AjaxResult getGroups(GroupConditions conditions) {
        PageableResult<GroupVo> pageableResult = groupService.getGroupsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/group/save", method = RequestMethod.POST, name = "保存班组")
    public AjaxResult saveGroup(Group group, Integer projectId) {
        groupService.saveGroup(group, projectId);
        return AjaxResult.success("保存班组成功");
    }
    @ResponseBody
    @RequestMapping(value = "/group/disable", method = RequestMethod.POST, name = "作废班组")
    public AjaxResult disableGroup(@RequestParam(name = "id") Integer id) {
        groupService.disableGroup(id);
        return AjaxResult.success("作废班组成功");
    }
    @ResponseBody
    @RequestMapping(value = "/group/delete", method = RequestMethod.POST, name = "删除班组")
    public AjaxResult deleteGroup(@RequestParam(name = "id") Integer id) {
        groupService.deleteGroup(id);
        return AjaxResult.success("删除班组成功");
    }
    //endregion

    //region 3.工种
    /**************************************** 3.工种 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/job/getJobs", name = "分页查询工种")
    public AjaxResult getJobs(JobConditions conditions) {
        PageableResult<Job> pageableResult = jobService.getJobsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/job/save", method = RequestMethod.POST, name = "保存工种")
    public AjaxResult saveJob(Job job) {
        jobService.saveJob(job);
        return AjaxResult.success("保存工种成功");
    }
    @ResponseBody
    @RequestMapping(value = "/job/disable", method = RequestMethod.POST, name = "作废工种")
    public AjaxResult disableJob(@RequestParam(name = "id") Integer id) {
        jobService.disableJob(id);
        return AjaxResult.success("作废工种成功");
    }
    @ResponseBody
    @RequestMapping(value = "/job/delete", method = RequestMethod.POST, name = "删除工种")
    public AjaxResult deleteJob(@RequestParam(name = "id") Integer id) {
        jobService.deleteJob(id);
        return AjaxResult.success("删除工种成功");
    }
    //endregion

    //region 4.单位
    /**************************************** 4.单位 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/company/getCompanies", name = "分页查询单位")
    public AjaxResult getCompanies(CompanyConditions conditions) {
        PageableResult<Company> pageableResult = companyService.getCompaniesByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/company/save", method = RequestMethod.POST, name = "保存单位")
    public AjaxResult saveCompany(Company company) {
        companyService.saveCompany(company);
        return AjaxResult.success("保存单位成功");
    }
    @ResponseBody
    @RequestMapping(value = "/company/disable", method = RequestMethod.POST, name = "作废单位")
    public AjaxResult disableCompany(@RequestParam(name = "id") Integer id) {
        companyService.disableCompany(id);
        return AjaxResult.success("作废单位成功");
    }
    @ResponseBody
    @RequestMapping(value = "/company/delete", method = RequestMethod.POST, name = "删除单位")
    public AjaxResult deleteCompany(@RequestParam(name = "id") Integer id) {
        companyService.deleteCompany(id);
        return AjaxResult.success("删除单位成功");
    }
    //endregion

    //region 5.项目
    /**************************************** 5.项目 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/project/getProjects", name = "分页查询项目")
    public AjaxResult getProjects(ProjectConditions conditions) {
        PageableResult<ProjectVo> pageableResult = projectService.getProjectsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/project/save", method = RequestMethod.POST, name = "保存项目")
    public AjaxResult saveProject(Project project) {
        projectService.saveProject(project);
        return AjaxResult.success("保存项目成功");
    }
    @ResponseBody
    @RequestMapping(value = "/project/disable", method = RequestMethod.POST, name = "作废项目")
    public AjaxResult disableProject(@RequestParam(name = "id") Integer id) {
        projectService.disableProject(id);
        return AjaxResult.success("作废项目成功");
    }
    @ResponseBody
    @RequestMapping(value = "/project/delete", method = RequestMethod.POST, name = "删除项目")
    public AjaxResult deleteProject(@RequestParam(name = "id") Integer id) {
        projectService.disableProject(id);
        return AjaxResult.success("删除项目成功");
    }
    //endregion
}

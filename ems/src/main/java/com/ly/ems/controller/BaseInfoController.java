package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.core.springmvc.controller.AbstractBaseController;
import com.ly.ems.model.base.company.Company;
import com.ly.ems.model.base.company.CompanyConditions;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.base.employee.EmployeeConditions;
import com.ly.ems.model.base.employee.EmployeeDTO;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.base.project.Project;
import com.ly.ems.model.base.project.ProjectConditions;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.service.base.BaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tidus on 2017/11/18.
 */
@Controller
@RequestMapping(value = "/base", name = "基础信息管理")
public class BaseInfoController extends AbstractBaseController {

    private Logger logger = LoggerFactory.getLogger(BaseInfoController.class);

    @Autowired
    BaseInfoService baseInfoService;

    /**************************************** 1.员工 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/employee/getEmployees", name = "分页查询员工")
    public AjaxResult getEmployees(EmployeeConditions conditions) {
        PageableResult<EmployeeDTO> pageableResult = baseInfoService.getEmployeesByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/employee/save", method = RequestMethod.POST, name = "保存员工")
    public AjaxResult saveEmployee(Employee employee) {
        try{
            baseInfoService.saveEmployee(employee);
        }catch (Exception ex){
            logger.error("保存员工失败", ex);
            return AjaxResult.fail("保存员工失败");
        }
        return AjaxResult.success("保存员工成功");
    }
    @ResponseBody
    @RequestMapping(value = "/employee/disable", method = RequestMethod.POST, name = "作废员工")
    public AjaxResult disableEmployee(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.disableEmployee(id);
        }catch (Exception ex){
            logger.error("作废员工失败", ex);
            return AjaxResult.fail("作废员工失败");
        }
        return AjaxResult.success("作废员工成功");
    }
    @ResponseBody
    @RequestMapping(value = "/employee/delete", method = RequestMethod.POST, name = "删除员工")
    public AjaxResult deleteEmployee(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.deleteEmployee(id);
        }catch (Exception ex){
            logger.error("删除员工失败", ex);
            return AjaxResult.fail("删除员工失败");
        }
        return AjaxResult.success("删除员工成功");
    }

    /**************************************** 2.班组 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/group/getGroups", name = "分页查询班组")
    public AjaxResult getGroups(GroupConditions conditions) {
        PageableResult<Group> pageableResult = baseInfoService.getGroupsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/group/save", method = RequestMethod.POST, name = "保存班组")
    public AjaxResult saveGroup(Group group) {
        try{
            baseInfoService.saveGroup(group);
        }catch (Exception ex){
            logger.error("保存班组失败", ex);
            return AjaxResult.fail("保存班组失败");
        }
        return AjaxResult.success("保存班组成功");
    }
    @ResponseBody
    @RequestMapping(value = "/group/disable", method = RequestMethod.POST, name = "作废班组")
    public AjaxResult disableGroup(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.disableGroup(id);
        }catch (Exception ex){
            logger.error("作废班组失败", ex);
            return AjaxResult.fail("作废班组失败");
        }
        return AjaxResult.success("作废班组成功");
    }
    @ResponseBody
    @RequestMapping(value = "/group/delete", method = RequestMethod.POST, name = "删除班组")
    public AjaxResult deleteGroup(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.deleteGroup(id);
        }catch (Exception ex){
            logger.error("删除班组失败", ex);
            return AjaxResult.fail("删除班组失败");
        }
        return AjaxResult.success("删除班组成功");
    }




    /**************************************** 3.工种 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/job/getJobs", name = "分页查询工种")
    public AjaxResult getJobs(JobConditions conditions) {
        PageableResult<Job> pageableResult = baseInfoService.getJobsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/job/save", method = RequestMethod.POST, name = "保存工种")
    public AjaxResult saveJob(Job job) {
        try{
            baseInfoService.saveJob(job);
        }catch (Exception ex){
            logger.error("保存工种失败", ex);
            return AjaxResult.fail("保存工种失败");
        }
        return AjaxResult.success("保存工种成功");
    }
    @ResponseBody
    @RequestMapping(value = "/job/disable", method = RequestMethod.POST, name = "作废工种")
    public AjaxResult disableJob(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.disableJob(id);
        }catch (Exception ex){
            logger.error("作废工种失败", ex);
            return AjaxResult.fail("作废工种失败");
        }
        return AjaxResult.success("作废工种成功");
    }
    @ResponseBody
    @RequestMapping(value = "/job/delete", method = RequestMethod.POST, name = "删除工种")
    public AjaxResult deleteJob(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.deleteJob(id);
        }catch (Exception ex){
            logger.error("删除工种失败", ex);
            return AjaxResult.fail("删除工种失败");
        }
        return AjaxResult.success("删除工种成功");
    }

    /**************************************** 4.单位 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/company/getCompanies", name = "分页查询单位")
    public AjaxResult getCompanies(CompanyConditions conditions) {
        PageableResult<Company> pageableResult = baseInfoService.getCompaniesByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/company/save", method = RequestMethod.POST, name = "保存单位")
    public AjaxResult saveCompany(Company company) {
        try{
            baseInfoService.saveCompany(company);
        }catch (Exception ex){
            logger.error("保存单位失败", ex);
            return AjaxResult.fail("保存单位失败");
        }
        return AjaxResult.success("保存单位成功");
    }
    @ResponseBody
    @RequestMapping(value = "/company/disable", method = RequestMethod.POST, name = "作废单位")
    public AjaxResult disableCompany(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.disableCompany(id);
        }catch (Exception ex){
            logger.error("作废单位失败", ex);
            return AjaxResult.fail("作废单位失败");
        }
        return AjaxResult.success("作废单位成功");
    }
    @ResponseBody
    @RequestMapping(value = "/company/delete", method = RequestMethod.POST, name = "删除单位")
    public AjaxResult deleteCompany(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.deleteCompany(id);
        }catch (Exception ex){
            logger.error("删除单位失败", ex);
            return AjaxResult.fail("删除单位失败");
        }
        return AjaxResult.success("删除单位成功");
    }

    /**************************************** 5.项目 ****************************************/
    @ResponseBody
    @RequestMapping(value = "/project/getProjects", name = "分页查询项目")
    public AjaxResult getProjects(ProjectConditions conditions) {
        PageableResult<Project> pageableResult = baseInfoService.getProjectsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
    @ResponseBody
    @RequestMapping(value = "/project/save", method = RequestMethod.POST, name = "保存项目")
    public AjaxResult saveProject(Project project) {
        try{
            baseInfoService.saveProject(project);
        }catch (Exception ex){
            logger.error("保存项目失败", ex);
            return AjaxResult.fail("保存项目失败");
        }
        return AjaxResult.success("保存项目成功");
    }
    @ResponseBody
    @RequestMapping(value = "/project/disable", method = RequestMethod.POST, name = "作废项目")
    public AjaxResult disableProject(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.disableCompany(id);
        }catch (Exception ex){
            logger.error("作废项目失败", ex);
            return AjaxResult.fail("作废项目失败");
        }
        return AjaxResult.success("作废项目成功");
    }
    @ResponseBody
    @RequestMapping(value = "/project/delete", method = RequestMethod.POST, name = "删除项目")
    public AjaxResult deleteProject(@RequestParam(name = "id") Integer id) {
        try{
            baseInfoService.deleteCompany(id);
        }catch (Exception ex){
            logger.error("删除项目失败", ex);
            return AjaxResult.fail("删除项目失败");
        }
        return AjaxResult.success("删除项目成功");
    }
}

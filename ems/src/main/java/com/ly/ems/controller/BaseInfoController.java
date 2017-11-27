package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.core.springmvc.controller.AbstractBaseController;
import com.ly.ems.model.base.area.Area;
import com.ly.ems.model.base.area.AreaConditions;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.model.base.job.JobConditions;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.service.BaseInfoService;
import com.ly.ems.model.base.group.GroupConditions;
import com.ly.ems.model.base.job.Job;
import com.ly.ems.service.BaseInfoService;
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


    @ResponseBody
    @RequestMapping(value = "/getGroups", name = "分页查询班组")
    public AjaxResult getGroups(GroupConditions conditions) {
        PageableResult<Group> pageableResult = baseInfoService.getGroupsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }

    @ResponseBody
    @RequestMapping(value = "/getAreas", name = "分页查询地区")
    public AjaxResult getAreas(AreaConditions conditions) {
        PageableResult<Area> pageableResult = baseInfoService.getAreasByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }


    /**
     * **************** 工种 ****************
     * @param conditions
     * @return
     */
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
            ex.printStackTrace();
            logger.error("保存工种失败");
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
            ex.printStackTrace();
            logger.error("作废工种失败");
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
            ex.printStackTrace();
            logger.error("删除工种失败");
            return AjaxResult.fail("删除工种失败");
        }
        return AjaxResult.success("删除工种成功");
    }
}

package com.liyuan.ems.controller;

import com.liyuan.ems.common.utils.AjaxResult;
import com.liyuan.ems.core.springmvc.controller.AbstractBaseController;
import com.liyuan.ems.model.base.area.Area;
import com.liyuan.ems.model.base.area.AreaConditions;
import com.liyuan.ems.model.base.group.Group;
import com.liyuan.ems.model.base.group.GroupConditions;
import com.liyuan.ems.model.base.job.Job;
import com.liyuan.ems.model.base.job.JobConditions;
import com.liyuan.ems.model.common.PageableResult;
import com.liyuan.ems.service.BaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @ResponseBody
    @RequestMapping(value = "/getJobs", name = "分页查询工种")
    public AjaxResult getJobs(JobConditions conditions) {
        PageableResult<Job> pageableResult = baseInfoService.getJobsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }
}

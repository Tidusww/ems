package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.core.springmvc.controller.AbstractBaseController;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceVo;
import com.ly.ems.model.base.employee.Employee;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.service.attendance.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tidus on 2017/11/18.
 */
@Controller
@RequestMapping(value = "/attendance", name = "考勤信息管理")
public class AttendanceController extends AbstractBaseController {

    private Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @Autowired
    AttendanceService attendanceService;

    @ResponseBody
    @RequestMapping(value = "/get", name = "分页查询考勤信息")
    public AjaxResult getAttendances(AttendanceConditions conditions) {
        PageableResult<AttendanceVo> pageableResult = attendanceService.getAttendances(conditions);
        return AjaxResult.success(pageableResult);
    }

    /**
     * 根据所选月份选择自动随机生成考勤信息
     * @param conditions
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/generate", method = RequestMethod.POST, name = "生成考勤信息")
    public AjaxResult generateAttendances(AttendanceConditions conditions) {
        attendanceService.generateAttendances(conditions);
        return AjaxResult.success("生成考勤信息成功");
    }


}

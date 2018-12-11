package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.common.utils.file.FileUtil;
import com.ly.ems.core.springmvc.controller.AbstractBaseController;
import com.ly.ems.model.attendance.Attendance;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceVo;
import com.ly.ems.model.base.group.Group;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.service.attendance.AttendanceService;
import com.ly.ems.service.base.BaseInfoService;
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
 * Created by tidus on 2017/11/18.
 */
@Controller
@RequestMapping(value = "/attendance", name = "考勤信息管理")
public class AttendanceController extends AbstractBaseController {

    private Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    private static final String ATTENDANCE_DETAIL_EXPORT_PREFIX = "attendance_detail";
    private static final String ATTENDANCE_DETAIL_EXPORT_TEMPLATE_PATH = "/excel/template/考勤明细模板表.xlsx";

    @Autowired
    BaseInfoService baseInfoService;

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


    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, name = "更新考勤信息")
    public AjaxResult updateAttendance(Attendance attendance, Date month) {
        attendanceService.updateAttendance(attendance, month);
        return AjaxResult.success("更新考勤信息成功");
    }


    /**
     * 3、导出工资发放表
     * @param request
     * @param response
     * @param conditions
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/exportAttendanceDetail", name = "导出考勤明细表")
    public AjaxResult exportAttendanceDetail(HttpServletRequest request, HttpServletResponse response, AttendanceConditions conditions) {

        List<AttendanceVo> salaryVoList = attendanceService.exportAttendanceDetail(conditions);
        String excelName = String.format("考勤明细表%s%s",
                DateFormatUtils.format(conditions.getAttendanceMonth(), DateUtil.YYYYMM),
                FileUtil.FILE_SUFFIX_XLSX);


        Group group = new Group();
        if(conditions.getGroupId() != null) {
            group.setId(conditions.getGroupId());
            group = baseInfoService.selectOneGroup(group);
        }


        // jxls 导出
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("month", DateFormatUtils.format(conditions.getAttendanceMonth(), "yyyy年MM月"));
        param.put("groupName", group.getGroupName());
        param.put("itemList", salaryVoList);

        String downloadName = String.format("%s%s", ATTENDANCE_DETAIL_EXPORT_PREFIX, FileUtil.FILE_SUFFIX_XLSX);
        String path = FileUtil.generateJxlsFile(ATTENDANCE_DETAIL_EXPORT_TEMPLATE_PATH, param, downloadName);

        // 返回结果
        AjaxResult result = AjaxResult.success("生成考勤明细excel成功");
        Map<String, String> fileData = new HashMap<String, String>();
        fileData.put("fileName", excelName);
        fileData.put("filePath", path);
        result.setData(fileData);
        return result;
    }
}

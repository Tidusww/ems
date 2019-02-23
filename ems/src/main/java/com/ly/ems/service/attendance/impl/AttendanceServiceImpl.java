package com.ly.ems.service.attendance.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.common.utils.ListUtil;
import com.ly.ems.common.utils.RandomUtil;
import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.dao.attendance.ExtendAttendanceMapper;
import com.ly.ems.dao.base.ExtendEmployeeMapper;
import com.ly.ems.model.attendance.AttendanceConditions;
import com.ly.ems.model.attendance.AttendanceVo;
import com.ly.ems.model.attendance.Attendance;
import com.ly.ems.model.attendance.constant.AttendanceConstant;
import com.ly.ems.model.attendance.constant.AttendanceStatusEnum;
import com.ly.ems.model.base.employee.EmployeeVo;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.common.constant.EnableEnum;
import com.ly.ems.service.attendance.AttendanceService;
import com.ly.ems.service.config.SystemConfigService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * Created by tidus on 2018/1/15.
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttendanceServiceImpl.class);


    @Autowired
    SystemConfigService systemConfigService;

    @Autowired
    ExtendEmployeeMapper extendEmployeeMapper;
    @Autowired
    ExtendAttendanceMapper extendAttendanceMapper;

    /**
     * 分页查询指定月份的考勤信息
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<AttendanceVo> getAttendances(AttendanceConditions conditions) {
        // 必须选定月份
        Date attendanceMonth = conditions.getAttendanceMonth();
        if (attendanceMonth == null) {
            throw new EMSRuntimeException("查看考勤数据前必须先选定月份!");
        }

        String attendanceMonthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        // 考勤数据必须存在
        if (!this.checkAttendanceExist(attendanceMonth)) {
            throw new EMSRuntimeException(String.format("月份%s的考勤数据不存在，请先生成数据!", attendanceMonthString));
        }

        // 查询数据
        List<AttendanceVo> resultList = extendAttendanceMapper.getAttendancesByConditions(conditions, this.getAttendanceTableNameByDate(attendanceMonth), attendanceMonthString, conditions.getCurrent(), conditions.getPageSize());
        PageInfo<AttendanceVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<AttendanceVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);

    }

    /**
     * 导出考勤明细
     * @param conditions
     * @return
     */
    @Override
    public List<AttendanceVo> exportAttendanceDetail(AttendanceConditions conditions) {
        // 必须选定月份
        Date attendanceMonth = conditions.getAttendanceMonth();
        if (attendanceMonth == null) {
            throw new EMSRuntimeException("导出考勤明细数据前请先选定月份");
        }

        // 按班组导出
        if(conditions.getGroupId() == null) {
            throw new EMSRuntimeException("导出考勤明细数据时请指定班组");
        }

        String attendanceMonthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        // 考勤数据必须存在
        if (!this.checkAttendanceExist(attendanceMonth)) {
            throw new EMSRuntimeException(String.format("月份%s的考勤数据不存在，请先生成数据!", attendanceMonthString));
        }


        // 不分页
        conditions.setCurrent(0);
        conditions.setPageSize(0);


        // 查询数据
        return extendAttendanceMapper.getAttendancesByConditions(conditions, this.getAttendanceTableNameByDate(attendanceMonth), attendanceMonthString, conditions.getCurrent(), conditions.getPageSize());

    }

    /**
     * 更新出勤信息
     * @param attendance
     */
    public void updateAttendance(Attendance attendance, Date attendanceMonth) {
        try {
            int row = extendAttendanceMapper.updateAttendanceById(this.getAttendanceTableNameByDate(attendanceMonth), attendance);
            if (row != 1) {
                LOGGER.error(String.format("expected update row should be 1 but found %d", row));
                throw new EMSRuntimeException("更新考勤信息失败");
            }
        } catch (Exception e) {
            LOGGER.error("更新考勤信息失败", e);
            throw new EMSRuntimeException("更新考勤信息失败");
        }
    }


    /**
     * 根据所选月份增量生成随机的考勤信息
     *
     * @param conditions
     */
    @Override
    public int generateAttendances(AttendanceConditions conditions) {
        Date attendanceMonth = conditions.getAttendanceMonth();
        if (attendanceMonth == null) {
            throw new EMSRuntimeException("生成考勤数据前请先选定月份!");
        }
        // 确保考勤表存在
        this.createAttendanceTable(attendanceMonth);

        List<Attendance> attendanceList = new ArrayList<Attendance>();
        try {
            long now = System.currentTimeMillis();
            // 获取当月未有考勤信息的有效员工
            String attendanceTableName = this.getAttendanceTableNameByDate(attendanceMonth);
            List<EmployeeVo> employeeVoList = extendEmployeeMapper.getEmployeeByNoAttendance(attendanceTableName);

            // 为每个员工生成当月的考勤信息
            for (EmployeeVo employeeVo : employeeVoList) {
                Attendance attendance = this.generateAttendanceForEmployeeRamdonly(employeeVo, attendanceMonth);
                attendanceList.add(attendance);
            }

            // 分页插入考勤信息
            if(attendanceList.size() > 0) {
                int pageSize = 0;
                while (true) {
                    List<Attendance> attendancePage = ListUtil.subList(attendanceList, pageSize, AttendanceConstant.BATCH_INSERT_PAGE_SIZE);
                    if (attendancePage == null || attendancePage.isEmpty()) {
                        break;
                    }
                    extendAttendanceMapper.batchInsert(attendanceTableName, attendancePage);
                    pageSize++;
                }
            }

            LOGGER.info("生成考勤记录成功，共{}条，耗时：{}", attendanceList.size(), System.currentTimeMillis()-now);
            return attendanceList.size();
        } catch (Exception e) {
            LOGGER.error("生成考勤记录失败", e);
            throw new EMSRuntimeException("生成考勤记录失败");
        }
    }


    /**
     * 自动随机生成指定月份的考勤数据（仅限当月有派遣信息）
     *
     * @param conditions
     */
    @Deprecated
    @Override
    public void generateAttendancesByDispatch(AttendanceConditions conditions) {
//        Date attendanceMonth = conditions.getAttendanceMonth();
//        if (attendanceMonth == null) {
//            throw new EMSRuntimeException("生成考勤数据前必须先选定月份!");
//        }
//
//        String attendanceMonthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
//        if (this.checkAttendanceExist(attendanceMonth)) {
//            throw new EMSRuntimeException(String.format("月份%s的考勤数据已存在!", attendanceMonthString));
//        }
//
//        try {
//            this.createAttendanceTable(attendanceMonth);
//        } catch (Exception e) {
//            LOGGER.error("创建考勤表失败", e);
//            throw new EMSRuntimeException("创建考勤表失败");
//        }
//
//        try {
//            // 获取当月有派遣关系的员工
//            List<EmployeeVo> employeeVoList = extendEmployeeMapper.getDispatchedEmployeeByMonth(attendanceMonth);
//
//            boolean isContinue = true;
//            int pageIndex = 1;
//            int pageSize = 500;
//            while (isContinue) {
//                // 分页获取员工信息
//                int formIndex = (pageIndex - 1) * pageSize;
//                int toIndex = pageIndex * pageSize;
//                if (toIndex > employeeVoList.size()) {
//                    toIndex = employeeVoList.size();
//                    isContinue = false;
//                }
//                List<EmployeeVo> employeeVoPage = employeeVoList.subList(formIndex, toIndex);
//
//                // 为每个员工生成当月的考勤信息
//                List<Attendance> attendanceList = new ArrayList<Attendance>();
//                for (EmployeeVo employeeVo : employeeVoPage) {
//                    Attendance attendance = this.generateAttendanceForEmployeeRamdonly(employeeVo, attendanceMonth);
//                    attendanceList.add(attendance);
//                }
//
//                if(attendanceList.size() > 0) {
//                    String attendanceTableName = AttendanceConstant.ATTENDANCE_TABLE_NAME_PRE + attendanceMonthString;
//                    extendAttendanceMapper.batchInsert(attendanceTableName, attendanceList);
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("生成考勤记录失败", e);
//            this.dropAttendanceTable(attendanceMonth);
//            throw new EMSRuntimeException("生成考勤记录失败");
//        }
    }

    /**
     * 为单个员工随机生成考勤记录
     * 0、不考虑上一个月的情况。
     * 1、周一到周五都正常上班。
     * 2、周六在上周日加班的情况下，必定休息；不然有30%的机会需要加班，
     * 3、周日在前一天加班的情况下，必定休息；不然有30%的机会需要加班。
     * 4、
     *
     * @param employeeVo
     * @return
     */
    private Attendance generateAttendanceForEmployeeRamdonly(EmployeeVo employeeVo, Date attendanceMonth) {
        Date[] monthDates = DateUtil.getMonthBoundary(attendanceMonth);
        Date startDate = monthDates[0];
        Date endDate = monthDates[1];

        Date nowDate = startDate;
        Map<String, Object> attendanceMap = new HashMap();
        while (!nowDate.after(endDate)) {
            // 几号
            int monthDay = DateUtil.getDayOfMonth(nowDate);
            // 周几
            int weekDay = DateUtil.getDayOfWeek(nowDate);

            // 用于设置出勤
            final String keyString = String.format("%s%d", AttendanceConstant.ATTENDANCE_STATUS_FIELD_PRE, monthDay);

            // 是否强制上班日
            if (systemConfigService.isForceWorking(nowDate)) {
                attendanceMap.put(keyString, AttendanceStatusEnum.ATTENDANCE);
            }

            // 是否节假日
            if (systemConfigService.isHoliday(nowDate)) {
                attendanceMap.put(keyString, AttendanceStatusEnum.VACATION);
            }

            // 周一到周五正常出勤
            if (1 <= weekDay && weekDay <= 5) {
                attendanceMap.put(keyString, AttendanceStatusEnum.ATTENDANCE);
            }

            // 周六
            if (weekDay == 6) {
                // TODO 上周日有没有加班
                attendanceMap.put(keyString,
                        RandomUtil.lottery(AttendanceConstant.OVERTIME_PROBABILITY) ?
                                AttendanceStatusEnum.OVERTIME : AttendanceStatusEnum.VACATION);
            }

            // 周日
            if (weekDay == 0) {
                if (monthDay == 1) {
                    // 第一天，直接看看要不要加班
                    attendanceMap.put(keyString,
                            RandomUtil.lottery(AttendanceConstant.OVERTIME_PROBABILITY) ?
                                    AttendanceStatusEnum.OVERTIME : AttendanceStatusEnum.VACATION);
                } else {
                    // 看看前一天周六有没有加班
                    final String preDayKeyString = String.format("%s%d", AttendanceConstant.ATTENDANCE_STATUS_FIELD_PRE, monthDay - 1);
                    AttendanceStatusEnum preAttendanceStatus = (AttendanceStatusEnum) attendanceMap.get(preDayKeyString);
                    if (preAttendanceStatus == AttendanceStatusEnum.OVERTIME) {
                        // 前一天周六加班了，必须休息
                        attendanceMap.put(keyString, AttendanceStatusEnum.VACATION);
                    } else {
                        attendanceMap.put(keyString,
                                RandomUtil.lottery(AttendanceConstant.OVERTIME_PROBABILITY) ?
                                        AttendanceStatusEnum.OVERTIME : AttendanceStatusEnum.VACATION);
                    }
                }
            }

            nowDate = DateUtil.nextDay(nowDate);
        }

        Attendance attendance = new AttendanceVo(attendanceMap);
        attendance.setEnable(EnableEnum.ENABLED);
        attendance.setEmployeeId(employeeVo.getId());
        return attendance;
    }


    /**
     * 查询指定月份的考勤数据是否存在
     *
     * @param attendanceMonth
     */
    private boolean checkAttendanceExist(Date attendanceMonth) {
        int exists = extendAttendanceMapper.isExistAttendanceTable(this.getAttendanceTableNameByDate(attendanceMonth));
        return exists > 0;
    }

    /**
     * 创建指定月份的考勤表
     *
     * @param attendanceMonth
     * @return
     */
    private void createAttendanceTable(Date attendanceMonth) {
        try {
            extendAttendanceMapper.createAttendanceTable(this.getAttendanceTableNameByDate(attendanceMonth));
        } catch (Exception e) {
            LOGGER.error("考勤表已存在，不需要创建", e);
        }
    }

    /**
     * 删除指定月份的考勤表
     *
     * @param attendanceMonth
     * @return
     */
    private void dropAttendanceTable(Date attendanceMonth) {
        extendAttendanceMapper.dropAttendanceTable(this.getAttendanceTableNameByDate(attendanceMonth));
    }

    //region 内部工具
    /**
     * 根据Date获取考勤表名
     * @param attendanceMonth
     * @return
     */
    private String getAttendanceTableNameByDate(Date attendanceMonth) {
        String attendanceMonthString = DateFormatUtils.format(attendanceMonth, DateUtil.YYYYMM);
        String attendanceTableName = MessageFormat.format(AttendanceConstant.ATTENDANCE_TABLE_NAME_PRE, attendanceMonthString);
        return attendanceTableName;
    }
    //endregion
}

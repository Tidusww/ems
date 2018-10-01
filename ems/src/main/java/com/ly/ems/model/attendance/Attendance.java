package com.ly.ems.model.attendance;

import com.ly.ems.model.attendance.constant.AttendanceStatusEnum;
import com.ly.ems.model.common.constant.EnableEnum;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`ly_attendance_template`")
public class Attendance {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 员工id
     */
    @Column(name = "`employee_id`")
    private Integer employeeId;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_1`")
    private AttendanceStatusEnum attendanceStatus1;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_2`")
    private AttendanceStatusEnum attendanceStatus2;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_3`")
    private AttendanceStatusEnum attendanceStatus3;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_4`")
    private AttendanceStatusEnum attendanceStatus4;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_5`")
    private AttendanceStatusEnum attendanceStatus5;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_6`")
    private AttendanceStatusEnum attendanceStatus6;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_7`")
    private AttendanceStatusEnum attendanceStatus7;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_8`")
    private AttendanceStatusEnum attendanceStatus8;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_9`")
    private AttendanceStatusEnum attendanceStatus9;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_10`")
    private AttendanceStatusEnum attendanceStatus10;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_11`")
    private AttendanceStatusEnum attendanceStatus11;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_12`")
    private AttendanceStatusEnum attendanceStatus12;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_13`")
    private AttendanceStatusEnum attendanceStatus13;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_14`")
    private AttendanceStatusEnum attendanceStatus14;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_15`")
    private AttendanceStatusEnum attendanceStatus15;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_16`")
    private AttendanceStatusEnum attendanceStatus16;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_17`")
    private AttendanceStatusEnum attendanceStatus17;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_18`")
    private AttendanceStatusEnum attendanceStatus18;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_19`")
    private AttendanceStatusEnum attendanceStatus19;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_20`")
    private AttendanceStatusEnum attendanceStatus20;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_21`")
    private AttendanceStatusEnum attendanceStatus21;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_22`")
    private AttendanceStatusEnum attendanceStatus22;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_23`")
    private AttendanceStatusEnum attendanceStatus23;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_24`")
    private AttendanceStatusEnum attendanceStatus24;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_25`")
    private AttendanceStatusEnum attendanceStatus25;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_26`")
    private AttendanceStatusEnum attendanceStatus26;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_27`")
    private AttendanceStatusEnum attendanceStatus27;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_28`")
    private AttendanceStatusEnum attendanceStatus28;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_29`")
    private AttendanceStatusEnum attendanceStatus29;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_30`")
    private AttendanceStatusEnum attendanceStatus30;

    /**
     * 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    @Column(name = "`attendance_status_31`")
    private AttendanceStatusEnum attendanceStatus31;

    /**
     * 启用状态, 0：禁用，1：启用
     */
    @Column(name = "`enable`")
    private EnableEnum enable;

    /**
     * 创建时间
     */
    @Column(name = "`create_date`")
    private Date createDate;

    /**
     * 最后更新时间
     */
    @Column(name = "`update_date`")
    private Date updateDate;

    /**
     * 更新人id
     */
    @Column(name = "`update_user_id`")
    private Integer updateUserId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取员工id
     *
     * @return employee_id - 员工id
     */
    public Integer getEmployeeId() {
        return employeeId;
    }

    /**
     * 设置员工id
     *
     * @param employeeId 员工id
     */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_1 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus1() {
        return attendanceStatus1;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus1 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus1(AttendanceStatusEnum attendanceStatus1) {
        this.attendanceStatus1 = attendanceStatus1;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_2 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus2() {
        return attendanceStatus2;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus2 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus2(AttendanceStatusEnum attendanceStatus2) {
        this.attendanceStatus2 = attendanceStatus2;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_3 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus3() {
        return attendanceStatus3;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus3 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus3(AttendanceStatusEnum attendanceStatus3) {
        this.attendanceStatus3 = attendanceStatus3;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_4 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus4() {
        return attendanceStatus4;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus4 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus4(AttendanceStatusEnum attendanceStatus4) {
        this.attendanceStatus4 = attendanceStatus4;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_5 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus5() {
        return attendanceStatus5;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus5 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus5(AttendanceStatusEnum attendanceStatus5) {
        this.attendanceStatus5 = attendanceStatus5;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_6 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus6() {
        return attendanceStatus6;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus6 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus6(AttendanceStatusEnum attendanceStatus6) {
        this.attendanceStatus6 = attendanceStatus6;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_7 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus7() {
        return attendanceStatus7;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus7 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus7(AttendanceStatusEnum attendanceStatus7) {
        this.attendanceStatus7 = attendanceStatus7;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_8 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus8() {
        return attendanceStatus8;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus8 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus8(AttendanceStatusEnum attendanceStatus8) {
        this.attendanceStatus8 = attendanceStatus8;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_9 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus9() {
        return attendanceStatus9;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus9 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus9(AttendanceStatusEnum attendanceStatus9) {
        this.attendanceStatus9 = attendanceStatus9;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_10 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus10() {
        return attendanceStatus10;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus10 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus10(AttendanceStatusEnum attendanceStatus10) {
        this.attendanceStatus10 = attendanceStatus10;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_11 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus11() {
        return attendanceStatus11;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus11 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus11(AttendanceStatusEnum attendanceStatus11) {
        this.attendanceStatus11 = attendanceStatus11;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_12 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus12() {
        return attendanceStatus12;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus12 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus12(AttendanceStatusEnum attendanceStatus12) {
        this.attendanceStatus12 = attendanceStatus12;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_13 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus13() {
        return attendanceStatus13;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus13 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus13(AttendanceStatusEnum attendanceStatus13) {
        this.attendanceStatus13 = attendanceStatus13;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_14 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus14() {
        return attendanceStatus14;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus14 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus14(AttendanceStatusEnum attendanceStatus14) {
        this.attendanceStatus14 = attendanceStatus14;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_15 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus15() {
        return attendanceStatus15;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus15 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus15(AttendanceStatusEnum attendanceStatus15) {
        this.attendanceStatus15 = attendanceStatus15;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_16 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus16() {
        return attendanceStatus16;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus16 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus16(AttendanceStatusEnum attendanceStatus16) {
        this.attendanceStatus16 = attendanceStatus16;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_17 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus17() {
        return attendanceStatus17;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus17 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus17(AttendanceStatusEnum attendanceStatus17) {
        this.attendanceStatus17 = attendanceStatus17;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_18 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus18() {
        return attendanceStatus18;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus18 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus18(AttendanceStatusEnum attendanceStatus18) {
        this.attendanceStatus18 = attendanceStatus18;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_19 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus19() {
        return attendanceStatus19;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus19 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus19(AttendanceStatusEnum attendanceStatus19) {
        this.attendanceStatus19 = attendanceStatus19;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_20 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus20() {
        return attendanceStatus20;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus20 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus20(AttendanceStatusEnum attendanceStatus20) {
        this.attendanceStatus20 = attendanceStatus20;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_21 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus21() {
        return attendanceStatus21;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus21 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus21(AttendanceStatusEnum attendanceStatus21) {
        this.attendanceStatus21 = attendanceStatus21;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_22 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus22() {
        return attendanceStatus22;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus22 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus22(AttendanceStatusEnum attendanceStatus22) {
        this.attendanceStatus22 = attendanceStatus22;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_23 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus23() {
        return attendanceStatus23;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus23 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus23(AttendanceStatusEnum attendanceStatus23) {
        this.attendanceStatus23 = attendanceStatus23;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_24 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus24() {
        return attendanceStatus24;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus24 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus24(AttendanceStatusEnum attendanceStatus24) {
        this.attendanceStatus24 = attendanceStatus24;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_25 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus25() {
        return attendanceStatus25;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus25 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus25(AttendanceStatusEnum attendanceStatus25) {
        this.attendanceStatus25 = attendanceStatus25;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_26 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus26() {
        return attendanceStatus26;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus26 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus26(AttendanceStatusEnum attendanceStatus26) {
        this.attendanceStatus26 = attendanceStatus26;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_27 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus27() {
        return attendanceStatus27;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus27 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus27(AttendanceStatusEnum attendanceStatus27) {
        this.attendanceStatus27 = attendanceStatus27;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_28 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus28() {
        return attendanceStatus28;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus28 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus28(AttendanceStatusEnum attendanceStatus28) {
        this.attendanceStatus28 = attendanceStatus28;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_29 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus29() {
        return attendanceStatus29;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus29 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus29(AttendanceStatusEnum attendanceStatus29) {
        this.attendanceStatus29 = attendanceStatus29;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_30 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus30() {
        return attendanceStatus30;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus30 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus30(AttendanceStatusEnum attendanceStatus30) {
        this.attendanceStatus30 = attendanceStatus30;
    }

    /**
     * 获取出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @return attendance_status_31 - 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public AttendanceStatusEnum getAttendanceStatus31() {
        return attendanceStatus31;
    }

    /**
     * 设置出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     *
     * @param attendanceStatus31 出勤状态, -1：休假，0：缺勤，1：正常出勤， 2：加班，3：请假
     */
    public void setAttendanceStatus31(AttendanceStatusEnum attendanceStatus31) {
        this.attendanceStatus31 = attendanceStatus31;
    }

    /**
     * 获取启用状态, 0：禁用，1：启用
     *
     * @return enable - 启用状态, 0：禁用，1：启用
     */
    public EnableEnum getEnable() {
        return enable;
    }

    /**
     * 设置启用状态, 0：禁用，1：启用
     *
     * @param enable 启用状态, 0：禁用，1：启用
     */
    public void setEnable(EnableEnum enable) {
        this.enable = enable;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取最后更新时间
     *
     * @return update_date - 最后更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置最后更新时间
     *
     * @param updateDate 最后更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取更新人id
     *
     * @return update_user_id - 更新人id
     */
    public Integer getUpdateUserId() {
        return updateUserId;
    }

    /**
     * 设置更新人id
     *
     * @param updateUserId 更新人id
     */
    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
}
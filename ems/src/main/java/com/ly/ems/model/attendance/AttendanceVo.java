package com.ly.ems.model.attendance;

import com.ly.ems.common.utils.ReflectUtil;
import com.ly.ems.model.attendance.constant.AttendanceConstant;
import com.ly.ems.model.attendance.constant.AttendanceStatusEnum;
import com.ly.ems.model.base.employee.constant.GenderEnum;

import java.util.Map;

/**
 * Created by tidus on 2018/9/29.
 */
public class AttendanceVo extends Attendance {
    private String numInGroup;
    private String month;

    private String employeeName;
    private GenderEnum gender;
    private String idCard;

    private Integer jobId;
    private String jobName;
    private Integer groupId;
    private String groupName;
    private Integer projectId;
    private String projectName;
    private Integer companyId;
    private String companyName;

    public AttendanceVo() {
        super();
    }
    public AttendanceVo(Map<String, Object> attendanceMap) {
        super();
        /**
         * 反射设置每天的出勤情况
         */
        for (int monthDay = 1; monthDay <= 31; monthDay++) {
            String keyString = String.format("%s%d", AttendanceConstant.ATTENDANCE_STATUS_FIELD_PRE, monthDay);
            AttendanceStatusEnum attendanceStatus = (AttendanceStatusEnum) attendanceMap.get(keyString);
            if (attendanceStatus != null) {
                try {
                    ReflectUtil.setFieldValueByFieldName(keyString, attendanceStatus, this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 获取总出勤次数（出勤+加班）
     *
     * @return
     */
    public Integer getAttendanceDays() throws NoSuchFieldException {
        Integer attendanceTimes = 0;
        for (int i = 1; i <= 31; i++) {
            String fieldName = String.format("%s%d", AttendanceConstant.ATTENDANCE_STATUS_FIELD_PRE, i);
            AttendanceStatusEnum status = (AttendanceStatusEnum)ReflectUtil.getFieldValueByFieldName(fieldName, this);
            if(status == AttendanceStatusEnum.ATTENDANCE || status == AttendanceStatusEnum.OVERTIME) {
                attendanceTimes ++;
            }
        }
        return attendanceTimes;

    }

    /**
     * Attendance_Status_Value
     *
     * @return
     */
    public String getAttendanceStatusValue1() {
        if (super.getAttendanceStatus1() == null) {
            return "";
        }
        return super.getAttendanceStatus1().getValue();
    }

    public String getAttendanceStatusValue2() {
        if (super.getAttendanceStatus2() == null) {
            return "";
        }
        return super.getAttendanceStatus2().getValue();
    }

    public String getAttendanceStatusValue3() {
        if (super.getAttendanceStatus3() == null) {
            return "";
        }
        return super.getAttendanceStatus3().getValue();
    }

    public String getAttendanceStatusValue4() {
        if (super.getAttendanceStatus4() == null) {
            return "";
        }
        return super.getAttendanceStatus4().getValue();
    }

    public String getAttendanceStatusValue5() {
        if (super.getAttendanceStatus5() == null) {
            return "";
        }
        return super.getAttendanceStatus5().getValue();
    }

    public String getAttendanceStatusValue6() {
        if (super.getAttendanceStatus6() == null) {
            return "";
        }
        return super.getAttendanceStatus6().getValue();
    }

    public String getAttendanceStatusValue7() {
        if (super.getAttendanceStatus7() == null) {
            return "";
        }
        return super.getAttendanceStatus7().getValue();
    }

    public String getAttendanceStatusValue8() {
        if (super.getAttendanceStatus8() == null) {
            return "";
        }
        return super.getAttendanceStatus8().getValue();
    }

    public String getAttendanceStatusValue9() {
        if (super.getAttendanceStatus9() == null) {
            return "";
        }
        return super.getAttendanceStatus9().getValue();
    }

    public String getAttendanceStatusValue10() {
        if (super.getAttendanceStatus10() == null) {
            return "";
        }
        return super.getAttendanceStatus10().getValue();
    }

    public String getAttendanceStatusValue11() {
        if (super.getAttendanceStatus11() == null) {
            return "";
        }
        return super.getAttendanceStatus11().getValue();
    }

    public String getAttendanceStatusValue12() {
        if (super.getAttendanceStatus12() == null) {
            return "";
        }
        return super.getAttendanceStatus12().getValue();
    }

    public String getAttendanceStatusValue13() {
        if (super.getAttendanceStatus13() == null) {
            return "";
        }
        return super.getAttendanceStatus13().getValue();
    }

    public String getAttendanceStatusValue14() {
        if (super.getAttendanceStatus14() == null) {
            return "";
        }
        return super.getAttendanceStatus14().getValue();
    }

    public String getAttendanceStatusValue15() {
        if (super.getAttendanceStatus15() == null) {
            return "";
        }
        return super.getAttendanceStatus15().getValue();
    }

    public String getAttendanceStatusValue16() {
        if (super.getAttendanceStatus16() == null) {
            return "";
        }
        return super.getAttendanceStatus16().getValue();
    }

    public String getAttendanceStatusValue17() {
        if (super.getAttendanceStatus17() == null) {
            return "";
        }
        return super.getAttendanceStatus17().getValue();
    }

    public String getAttendanceStatusValue18() {
        if (super.getAttendanceStatus18() == null) {
            return "";
        }
        return super.getAttendanceStatus18().getValue();
    }

    public String getAttendanceStatusValue19() {
        if (super.getAttendanceStatus19() == null) {
            return "";
        }
        return super.getAttendanceStatus19().getValue();
    }

    public String getAttendanceStatusValue20() {
        if (super.getAttendanceStatus20() == null) {
            return "";
        }
        return super.getAttendanceStatus20().getValue();
    }

    public String getAttendanceStatusValue21() {
        if (super.getAttendanceStatus21() == null) {
            return "";
        }
        return super.getAttendanceStatus21().getValue();
    }

    public String getAttendanceStatusValue22() {
        if (super.getAttendanceStatus22() == null) {
            return "";
        }
        return super.getAttendanceStatus22().getValue();
    }

    public String getAttendanceStatusValue23() {
        if (super.getAttendanceStatus23() == null) {
            return "";
        }
        return super.getAttendanceStatus23().getValue();
    }

    public String getAttendanceStatusValue24() {
        if (super.getAttendanceStatus24() == null) {
            return "";
        }
        return super.getAttendanceStatus24().getValue();
    }

    public String getAttendanceStatusValue25() {
        if (super.getAttendanceStatus25() == null) {
            return "";
        }
        return super.getAttendanceStatus25().getValue();
    }

    public String getAttendanceStatusValue26() {
        if (super.getAttendanceStatus26() == null) {
            return "";
        }
        return super.getAttendanceStatus26().getValue();
    }

    public String getAttendanceStatusValue27() {
        if (super.getAttendanceStatus27() == null) {
            return "";
        }
        return super.getAttendanceStatus27().getValue();
    }

    public String getAttendanceStatusValue28() {
        if (super.getAttendanceStatus28() == null) {
            return "";
        }
        return super.getAttendanceStatus28().getValue();
    }

    public String getAttendanceStatusValue29() {
        if (super.getAttendanceStatus29() == null) {
            return "";
        }
        return super.getAttendanceStatus29().getValue();
    }

    public String getAttendanceStatusValue30() {
        if (super.getAttendanceStatus30() == null) {
            return "";
        }
        return super.getAttendanceStatus30().getValue();
    }

    public String getAttendanceStatusValue31() {
        if (super.getAttendanceStatus31() == null) {
            return "";
        }
        return super.getAttendanceStatus31().getValue();
    }

    public String getGenderValue() {
        if (this.getGender() == null) {
            return "";
        }
        return this.getGender().getValue();
    }

    public String getNumInGroup() {
        return numInGroup;
    }

    public void setNumInGroup(String numInGroup) {
        this.numInGroup = numInGroup;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

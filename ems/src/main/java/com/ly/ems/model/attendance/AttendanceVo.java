package com.ly.ems.model.attendance;

import com.ly.ems.model.attendance.constant.AttendanceConstant;
import com.ly.ems.model.attendance.constant.AttendanceStatusEnum;

import java.util.Map;

/**
 * Created by tidus on 2018/9/29.
 */
public class AttendanceVo extends Attendance {
    private String employeeName;
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
        for(int monthDay = 1; monthDay <=31; monthDay++) {
            String keyString = String.format("%s%d", AttendanceConstant.ATTENDANCE_STATUS_KEY_PRE, monthDay);
            AttendanceStatusEnum attendanceStatus = (AttendanceStatusEnum)attendanceMap.get(keyString);
            if(attendanceStatus != null) {
                if(monthDay == 1) {
                    super.setAttendanceStatus1(attendanceStatus);
                }
                if(monthDay == 2) {
                    super.setAttendanceStatus2(attendanceStatus);
                }
                if(monthDay == 3) {
                    super.setAttendanceStatus3(attendanceStatus);
                }
                if(monthDay == 4) {
                    super.setAttendanceStatus4(attendanceStatus);
                }
                if(monthDay == 5) {
                    super.setAttendanceStatus5(attendanceStatus);
                }
                if(monthDay == 6) {
                    super.setAttendanceStatus6(attendanceStatus);
                }
                if(monthDay == 7) {
                    super.setAttendanceStatus7(attendanceStatus);
                }
                if(monthDay == 8) {
                    super.setAttendanceStatus8(attendanceStatus);
                }
                if(monthDay == 9) {
                    super.setAttendanceStatus9(attendanceStatus);
                }
                if(monthDay == 10) {
                    super.setAttendanceStatus10(attendanceStatus);
                }
                if(monthDay == 11) {
                    super.setAttendanceStatus11(attendanceStatus);
                }
                if(monthDay == 12) {
                    super.setAttendanceStatus12(attendanceStatus);
                }
                if(monthDay == 13) {
                    super.setAttendanceStatus13(attendanceStatus);
                }
                if(monthDay == 14) {
                    super.setAttendanceStatus14(attendanceStatus);
                }
                if(monthDay == 15) {
                    super.setAttendanceStatus15(attendanceStatus);
                }
                if(monthDay == 16) {
                    super.setAttendanceStatus16(attendanceStatus);
                }
                if(monthDay == 17) {
                    super.setAttendanceStatus17(attendanceStatus);
                }
                if(monthDay == 18) {
                    super.setAttendanceStatus18(attendanceStatus);
                }
                if(monthDay == 19) {
                    super.setAttendanceStatus19(attendanceStatus);
                }
                if(monthDay == 20) {
                    super.setAttendanceStatus20(attendanceStatus);
                }
                if(monthDay == 21) {
                    super.setAttendanceStatus21(attendanceStatus);
                }
                if(monthDay == 22) {
                    super.setAttendanceStatus22(attendanceStatus);
                }
                if(monthDay == 23) {
                    super.setAttendanceStatus23(attendanceStatus);
                }
                if(monthDay == 24) {
                    super.setAttendanceStatus24(attendanceStatus);
                }
                if(monthDay == 25) {
                    super.setAttendanceStatus25(attendanceStatus);
                }
                if(monthDay == 26) {
                    super.setAttendanceStatus26(attendanceStatus);
                }
                if(monthDay == 27) {
                    super.setAttendanceStatus27(attendanceStatus);
                }
                if(monthDay == 28) {
                    super.setAttendanceStatus28(attendanceStatus);
                }
                if(monthDay == 29) {
                    super.setAttendanceStatus29(attendanceStatus);
                }
                if(monthDay == 30) {
                    super.setAttendanceStatus30(attendanceStatus);
                }
                if(monthDay == 31) {
                    super.setAttendanceStatus31(attendanceStatus);
                }

            }
        }


    }

    /**
     * Attendance_Status_Value
     * @return
     */
    public String getAttendanceStatusValue1() {
        if(super.getAttendanceStatus1() == null) {
            return "";
        }
        return super.getAttendanceStatus1().getValue();
    }
    public String getAttendanceStatusValue2() {
        if(super.getAttendanceStatus2() == null) {
            return "";
        }
        return super.getAttendanceStatus2().getValue();
    }
    public String getAttendanceStatusValue3() {
        if(super.getAttendanceStatus3() == null) {
            return "";
        }
        return super.getAttendanceStatus3().getValue();
    }
    public String getAttendanceStatusValue4() {
        if(super.getAttendanceStatus4() == null) {
            return "";
        }
        return super.getAttendanceStatus4().getValue();
    }
    public String getAttendanceStatusValue5() {
        if(super.getAttendanceStatus5() == null) {
            return "";
        }
        return super.getAttendanceStatus5().getValue();
    }
    public String getAttendanceStatusValue6() {
        if(super.getAttendanceStatus6() == null) {
            return "";
        }
        return super.getAttendanceStatus6().getValue();
    }
    public String getAttendanceStatusValue7() {
        if(super.getAttendanceStatus7() == null) {
            return "";
        }
        return super.getAttendanceStatus7().getValue();
    }
    public String getAttendanceStatusValue8() {
        if(super.getAttendanceStatus8() == null) {
            return "";
        }
        return super.getAttendanceStatus8().getValue();
    }
    public String getAttendanceStatusValue9() {
        if(super.getAttendanceStatus9() == null) {
            return "";
        }
        return super.getAttendanceStatus9().getValue();
    }
    public String getAttendanceStatusValue10() {
        if(super.getAttendanceStatus10() == null) {
            return "";
        }
        return super.getAttendanceStatus10().getValue();
    }
    public String getAttendanceStatusValue11() {
        if(super.getAttendanceStatus11() == null) {
            return "";
        }
        return super.getAttendanceStatus11().getValue();
    }
    public String getAttendanceStatusValue12() {
        if(super.getAttendanceStatus12() == null) {
            return "";
        }
        return super.getAttendanceStatus12().getValue();
    }
    public String getAttendanceStatusValue13() {
        if(super.getAttendanceStatus13() == null) {
            return "";
        }
        return super.getAttendanceStatus13().getValue();
    }
    public String getAttendanceStatusValue14() {
        if(super.getAttendanceStatus14() == null) {
            return "";
        }
        return super.getAttendanceStatus14().getValue();
    }
    public String getAttendanceStatusValue15() {
        if(super.getAttendanceStatus15() == null) {
            return "";
        }
        return super.getAttendanceStatus15().getValue();
    }
    public String getAttendanceStatusValue16() {
        if(super.getAttendanceStatus16() == null) {
            return "";
        }
        return super.getAttendanceStatus16().getValue();
    }
    public String getAttendanceStatusValue17() {
        if(super.getAttendanceStatus17() == null) {
            return "";
        }
        return super.getAttendanceStatus17().getValue();
    }
    public String getAttendanceStatusValue18() {
        if(super.getAttendanceStatus18() == null) {
            return "";
        }
        return super.getAttendanceStatus18().getValue();
    }
    public String getAttendanceStatusValue19() {
        if(super.getAttendanceStatus19() == null) {
            return "";
        }
        return super.getAttendanceStatus19().getValue();
    }
    public String getAttendanceStatusValue20() {
        if(super.getAttendanceStatus20() == null) {
            return "";
        }
        return super.getAttendanceStatus20().getValue();
    }
    public String getAttendanceStatusValue21() {
        if(super.getAttendanceStatus21() == null) {
            return "";
        }
        return super.getAttendanceStatus21().getValue();
    }
    public String getAttendanceStatusValue22() {
        if(super.getAttendanceStatus22() == null) {
            return "";
        }
        return super.getAttendanceStatus22().getValue();
    }
    public String getAttendanceStatusValue23() {
        if(super.getAttendanceStatus23() == null) {
            return "";
        }
        return super.getAttendanceStatus23().getValue();
    }
    public String getAttendanceStatusValue24() {
        if(super.getAttendanceStatus24() == null) {
            return "";
        }
        return super.getAttendanceStatus24().getValue();
    }
    public String getAttendanceStatusValue25() {
        if(super.getAttendanceStatus25() == null) {
            return "";
        }
        return super.getAttendanceStatus25().getValue();
    }
    public String getAttendanceStatusValue26() {
        if(super.getAttendanceStatus26() == null) {
            return "";
        }
        return super.getAttendanceStatus26().getValue();
    }
    public String getAttendanceStatusValue27() {
        if(super.getAttendanceStatus27() == null) {
            return "";
        }
        return super.getAttendanceStatus27().getValue();
    }
    public String getAttendanceStatusValue28() {
        if(super.getAttendanceStatus28() == null) {
            return "";
        }
        return super.getAttendanceStatus28().getValue();
    }
    public String getAttendanceStatusValue29() {
        if(super.getAttendanceStatus29() == null) {
            return "";
        }
        return super.getAttendanceStatus29().getValue();
    }
    public String getAttendanceStatusValue30() {
        if(super.getAttendanceStatus30() == null) {
            return "";
        }
        return super.getAttendanceStatus30().getValue();
    }
    public String getAttendanceStatusValue31() {
        if(super.getAttendanceStatus31() == null) {
            return "";
        }
        return super.getAttendanceStatus31().getValue();
    }





    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

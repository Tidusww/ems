package com.ly.ems.model.attendance.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.ly.ems.core.mybatis.BaseKeyValueEnum;

/**
 * Created by tidus on 2018/1/15.
 */
public enum AttendanceStatusEnum implements BaseKeyValueEnum {
    /**
     * 性别：
     * -1: 休假
     * 0: 缺勤
     * 1: 出勤
     * 2: 加班
     * 3: 请假
     */
    VACATION(-1, "休假"),
    ABSENCE(0, "缺勤"),
    ATTENDANCE(1, "出勤"),
    OVERTIME(2, "加班"),
    LEAVE(3, "请假");


    private Integer key;
    private String value;

    AttendanceStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    @JsonValue
    @Override
    public Integer getKey() {
        return key;
    }
    @Override
    public void setKey(Integer key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }
    @Override
    public void setValue(String value) {
        this.value = value;
    }
}

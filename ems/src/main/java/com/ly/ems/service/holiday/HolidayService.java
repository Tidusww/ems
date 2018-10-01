package com.ly.ems.service.holiday;

import java.util.Date;

/**
 * Created by tidus on 2018/10/1.
 */
public interface HolidayService {

    boolean isHoliday(Date date);

    boolean isForceWorkingDate(Date date);
}

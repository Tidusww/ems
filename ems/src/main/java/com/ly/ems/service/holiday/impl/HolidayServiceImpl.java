package com.ly.ems.service.holiday.impl;

import com.ly.ems.service.holiday.HolidayService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by tidus on 2018/10/1.
 */
@Service
public class HolidayServiceImpl implements HolidayService {


    /**
     * 是否节假日
     * 手工配置的法定节假日
     * @param date
     * @return
     */
    @Override
    public boolean isHoliday(Date date) {
        // TODO


        return false;
    }

    /**
     * 是否强制工作日
     *
     * @param date
     * @return
     */
    @Override
    public boolean isForceWorkingDate(Date date) {
        // TODO

        return false;
    }

}

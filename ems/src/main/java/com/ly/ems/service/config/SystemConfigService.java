package com.ly.ems.service.config;

import java.util.Date;

/**
 * Created by tidus on 2018/10/1.
 */
public interface SystemConfigService {

    /**
     * 是否节假日
     * @param date
     * @return
     */
    boolean isHoliday(Date date);

    /**
     * 是否强制上班日
     * @param date
     * @return
     */
    boolean isForceWorking(Date date);

    /**
     * 是否高温补贴月份
     * @param date
     * @return
     */
    boolean isHotAllowanceDate(Date date);

    /**
     * 获取基本工资（元/月）
     * @return
     */
    float getBasicSalary();

    /**
     * 获取高温补贴（元/日）
     * @return
     */
    float getHotAllowance();

    /**
     * 获取社保补贴（元/日）
     * @return
     */
    float getSocialSecurityAllowance();

    /**
     * 获取住房补贴（元/日）
     * @return
     */
    float getHouseFundAllowance();


}

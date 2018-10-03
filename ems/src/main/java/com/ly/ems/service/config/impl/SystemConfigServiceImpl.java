package com.ly.ems.service.config.impl;

import com.ly.ems.service.config.SystemConfigService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by tidus on 2018/10/1.
 */
@Cacheable
@Service
public class SystemConfigServiceImpl implements SystemConfigService {


    /**
     * 1、是否节假日
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
     * 2、是否强制工作日
     *
     * @param date
     * @return
     */
    @Override
    public boolean isForceWorking(Date date) {
        // TODO

        return false;
    }

    /**
     * 是否高温补贴月份
     * @param date
     * @return
     */
    @Override
    public boolean isHotAllowanceDate(Date date) {
        // TODO
        return false;
    }

    /**
     * 获取基本工资（元/月）
     * @return
     */
    @Override
    public double getBasicSalary() {
        return 0.f;
    }

    /**
     * 获取高温补贴（元/日）
     * @return
     */
    @Override
    public double getHotAllowance() {
        return 0.f;
    }

    /**
     * 获取社保补贴（元/日）
     * @return
     */
    @Override
    public double getSocialSecurityAllowance() {
        return 0.f;
    }

    /**
     * 获取住房补贴（元/日）
     * @return
     */
    @Override
    public double getHouseFundAllowance() {
        return 0.f;
    }
}

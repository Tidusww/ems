package com.ly.ems.service.config.impl;

import com.ly.ems.dao.config.SystemConfigMapper;
import com.ly.ems.model.config.SystemConfig;
import com.ly.ems.model.config.constant.SystemConfigTypeEnum;
import com.ly.ems.service.config.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by tidus on 2018/10/1.
 */
@CacheConfig(cacheNames = "systemConfigCache", keyGenerator = "keyGenerator")
@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfigServiceImpl.class);


    @Autowired
    SystemConfigMapper systemConfigMapper;

    /**
     * 1、是否节假日
     * 手工配置的法定节假日
     *
     * @param date
     * @return
     */
    @Override
    @Cacheable(key = "'SystemConfigService.isHoliday_'+#date")
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
    @Cacheable(key = "'SystemConfigService.isForceWorking_'+#date")
    public boolean isForceWorking(Date date) {
        // TODO

        return false;
    }

    /**
     * 3、是否高温补贴月份
     *
     * @param date
     * @return
     */
    @Override
    @Cacheable(key = "'SystemConfigService.isHotAllowanceDate_'+#date")
    public boolean isHotAllowanceDate(Date date) {
        // TODO
        return false;
    }

    /**
     * 4、获取基本工资（元/月）
     *
     * @return
     */
    @Override
    @Cacheable(key = "'SystemConfigService.getBasicSalary'")
    public double getBasicSalary() {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setConfigType(SystemConfigTypeEnum.BASIC_SALARY);
        try {
            systemConfig = systemConfigMapper.selectOne(systemConfig);
            if(systemConfig != null && !StringUtils.isEmpty(systemConfig.getConfigValue())) {
                return Double.parseDouble(systemConfig.getConfigValue());
            }else {
                LOGGER.error("没有配置基本工资");
                return 0.d;
            }
        } catch (Exception e) {
            LOGGER.error("获取基本工资出错", e);
            return 0.d;
        }
    }

    /**
     * 5、获取高温补贴（元/日）
     *
     * @return
     */
    @Override
    @Cacheable(key = "'SystemConfigService.getHotAllowance'")
    public double getHotAllowance() {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setConfigType(SystemConfigTypeEnum.HOT_ALLOWANCE);
        try {
            systemConfig = systemConfigMapper.selectOne(systemConfig);
            if(systemConfig != null && !StringUtils.isEmpty(systemConfig.getConfigValue())) {
                return Double.parseDouble(systemConfig.getConfigValue());
            }else {
                LOGGER.error("没有配置高温补贴");
                return 0.d;
            }
        } catch (Exception e) {
            LOGGER.error("获取高温补贴出错", e);
            return 0.d;
        }
    }

    /**
     * 6、获取社保补贴（元/日）
     *
     * @return
     */
    @Override
    @Cacheable(key = "'SystemConfigService.getSocialSecurityAllowance'")
    public double getSocialSecurityAllowance() {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setConfigType(SystemConfigTypeEnum.SOCIAL_SECURITY_ALLOWANCE);
        try {
            systemConfig = systemConfigMapper.selectOne(systemConfig);
            if(systemConfig != null && !StringUtils.isEmpty(systemConfig.getConfigValue())) {
                return Double.parseDouble(systemConfig.getConfigValue());
            }else {
                LOGGER.error("没有配置社保补贴");
                return 0.d;
            }
        } catch (Exception e) {
            LOGGER.error("获取社保补贴出错", e);
            return 0.d;
        }
    }

    /**
     * 7、获取住房补贴（元/日）
     *
     * @return
     */
    @Override
    @Cacheable(key = "'SystemConfigService.getHouseFundAllowance'")
    public double getHouseFundAllowance() {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setConfigType(SystemConfigTypeEnum.HOUSE_FUND_ALLOWANCE);
        try {
            systemConfig = systemConfigMapper.selectOne(systemConfig);
            if(systemConfig != null && !StringUtils.isEmpty(systemConfig.getConfigValue())) {
                return Double.parseDouble(systemConfig.getConfigValue());
            }else {
                LOGGER.error("没有配置住房补贴");
                return 0.d;
            }
        } catch (Exception e) {
            LOGGER.error("获取住房补贴出错", e);
            return 0.d;
        }
    }
}

package com.ly.ems.service.config.impl;

import com.github.pagehelper.PageInfo;
import com.ly.ems.common.utils.DateUtil;
import com.ly.ems.core.exception.EMSBusinessException;
import com.ly.ems.core.exception.EMSRuntimeException;
import com.ly.ems.dao.config.ExtendSystemConfigMapper;
import com.ly.ems.dao.config.SystemConfigMapper;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.config.SystemConfig;
import com.ly.ems.model.config.SystemConfigCondition;
import com.ly.ems.model.config.SystemConfigVo;
import com.ly.ems.model.config.constant.SystemConfigTypeEnum;
import com.ly.ems.service.config.SystemConfigService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by tidus on 2018/10/1.
 */
@CacheConfig(cacheNames = "systemConfigCache", keyGenerator = "keyGenerator")
@Service
public class SystemConfigServiceImpl implements SystemConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfigServiceImpl.class);


    @Autowired
    SystemConfigMapper systemConfigMapper;

    @Autowired
    ExtendSystemConfigMapper extendSystemConfigMapper;


    /**
     * 分页查询
     *
     * @param conditions
     * @return
     */
    @Override
    public PageableResult<SystemConfigVo> getConfigs(SystemConfigCondition conditions) {

        List<SystemConfigVo> resultList = extendSystemConfigMapper.selectByConditions(conditions);
        PageInfo<SystemConfigVo> pageInfo = new PageInfo(resultList);

        return new PageableResult<SystemConfigVo>((int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), resultList);
    }

    /**
     * 更新系统参数
     * 'HOT_ALLOWANCE', 'SOCIAL_SECURITY_ALLOWANCE', 'HOUSE_FUND_ALLOWANCE' 只能有一条记录
     * 'HOLIDAY', 'OVERTIME_DAY', 'HOT_ALLOWANCE_BEGIN_MONTH', 'HOT_ALLOWANCE_END_MONTH' 值不允许重复
     *
     * @param systemConfig
     */
    @Override
    public void saveSystemConfig(SystemConfig systemConfig) {
        SystemConfigTypeEnum systemConfigType = systemConfig.getConfigType();
        if (systemConfigType == SystemConfigTypeEnum.HOT_ALLOWANCE
                || systemConfigType == SystemConfigTypeEnum.SOCIAL_SECURITY_ALLOWANCE
                || systemConfigType == SystemConfigTypeEnum.HOUSE_FUND_ALLOWANCE) {
            this.saveSingletonConfig(systemConfig);
        } else {
            this.saveNormalConfig(systemConfig);
        }
    }

    /**
     * 保存只能有一条的参数记录
     *
     * @param systemConfig
     */
    private void saveSingletonConfig(SystemConfig systemConfig) {
        if (systemConfig.getId() == null) {
            // 新增
            SystemConfig configCondition = new SystemConfig();
            configCondition.setConfigType(systemConfig.getConfigType());
            SystemConfig oneSystemConfig = this.getOneSystemConfigByConfigType(configCondition);
            if (oneSystemConfig != null) {
                throw new EMSRuntimeException("当前系统参数已存在，请进行编辑操作");
            }
            systemConfigMapper.insertSelective(systemConfig);
        } else {
            // 修改
            SystemConfig configCondition = new SystemConfig();
            configCondition.setConfigType(systemConfig.getConfigType());
            SystemConfig oneSystemConfig = this.getOneSystemConfigByConfigType(configCondition);
            if (!oneSystemConfig.getId().equals(systemConfig.getId())) {
                // 一般不会出现
                throw new EMSRuntimeException("当前系统参数已存在，请进行编辑操作");
            }
            SystemConfig updateSystemConfig = new SystemConfig();
            // 以防万一，id和type都取从数据库查询出的数据，确保数据唯一
            updateSystemConfig.setId(oneSystemConfig.getId());
            updateSystemConfig.setConfigType(oneSystemConfig.getConfigType());
            updateSystemConfig.setConfigValue(systemConfig.getConfigValue());
            updateSystemConfig.setConfigDesc(systemConfig.getConfigDesc());
            systemConfigMapper.updateByPrimaryKeySelective(updateSystemConfig);
        }
    }

    private SystemConfig getOneSystemConfigByConfigType(SystemConfig configCondition) {
        // 查询当前是否已有配置
        SystemConfig one = null;
        try {
            one = systemConfigMapper.selectOne(configCondition);
        } catch (Exception e) {
            LOGGER.error("配置记录不唯一", e);
            throw new EMSRuntimeException("当前系统配置不唯一，请联系管理员");
        }
        return one;
    }

    /**
     * 保存可以有多条但不能值重复的参数
     *
     * @param systemConfig
     */
    private void saveNormalConfig(SystemConfig systemConfig) {
        if (systemConfig.getId() == null) {
            // 新增
            SystemConfig configCondition = new SystemConfig();
            configCondition.setConfigType(systemConfig.getConfigType());
            configCondition.setConfigValue(systemConfig.getConfigValue());// 值不能重复
            SystemConfig oneSystemConfig = this.getOneSystemConfigByConfigType(configCondition);
            if (oneSystemConfig != null) {
                throw new EMSRuntimeException("当前系统参数值已存在，请输入其他参数值或编辑该参数");
            }
            systemConfigMapper.insertSelective(systemConfig);
        } else {
            // 修改
            SystemConfig configCondition = new SystemConfig();
            configCondition.setConfigType(systemConfig.getConfigType());
            configCondition.setConfigValue(systemConfig.getConfigValue());// 值不能重复
            SystemConfig oneSystemConfig = this.getOneSystemConfigByConfigType(configCondition);
            if (!oneSystemConfig.getId().equals(systemConfig.getId())) {
                // 当前已存在另一条类型-值都一样的记录，不允许
                throw new EMSRuntimeException("当前系统参数与已有的重复，请确认");
            }
            SystemConfig updateSystemConfig = new SystemConfig();
            updateSystemConfig.setId(systemConfig.getId());
            updateSystemConfig.setConfigType(systemConfig.getConfigType());
            updateSystemConfig.setConfigValue(systemConfig.getConfigValue());
            updateSystemConfig.setConfigDesc(systemConfig.getConfigDesc());
            systemConfigMapper.updateByPrimaryKeySelective(updateSystemConfig);
        }
    }

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
        String dateString = DateFormatUtils.format(date, DateUtil.YYYY_MM_DD);
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setConfigType(SystemConfigTypeEnum.HOLIDAY);
        systemConfig.setConfigValue(dateString);

        systemConfig = systemConfigMapper.selectOne(systemConfig);

        if(systemConfig != null && systemConfig.getId() != null) {
            // 记录存在
            return true;
        }

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

        String dateString = DateFormatUtils.format(date, DateUtil.YYYY_MM_DD);
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setConfigType(SystemConfigTypeEnum.OVERTIME_DAY);
        systemConfig.setConfigValue(dateString);

        systemConfig = systemConfigMapper.selectOne(systemConfig);

        if(systemConfig != null && systemConfig.getId() != null) {
            // 记录存在
            return true;
        }

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
     * 4、获取高温补贴（元/日）
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
            if (systemConfig != null && !StringUtils.isEmpty(systemConfig.getConfigValue())) {
                return Double.parseDouble(systemConfig.getConfigValue());
            } else {
                LOGGER.error("没有配置高温补贴");
                return 0.d;
            }
        } catch (Exception e) {
            LOGGER.error("获取高温补贴出错", e);
            return 0.d;
        }
    }

    /**
     * 5、获取社保补贴（元/日）
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
            if (systemConfig != null && !StringUtils.isEmpty(systemConfig.getConfigValue())) {
                return Double.parseDouble(systemConfig.getConfigValue());
            } else {
                LOGGER.error("没有配置社保补贴");
                return 0.d;
            }
        } catch (Exception e) {
            LOGGER.error("获取社保补贴出错", e);
            return 0.d;
        }
    }

    /**
     * 6、获取住房补贴（元/日）
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
            if (systemConfig != null && !StringUtils.isEmpty(systemConfig.getConfigValue())) {
                return Double.parseDouble(systemConfig.getConfigValue());
            } else {
                LOGGER.error("没有配置住房补贴");
                return 0.d;
            }
        } catch (Exception e) {
            LOGGER.error("获取住房补贴出错", e);
            return 0.d;
        }
    }
}

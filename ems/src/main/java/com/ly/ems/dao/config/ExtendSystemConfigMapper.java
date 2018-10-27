package com.ly.ems.dao.config;

import com.ly.ems.core.mybatis.dao.ConditionMapper;
import com.ly.ems.model.config.SystemConfigCondition;
import com.ly.ems.model.config.SystemConfigVo;

/**
 * Created by tidus on 2018/10/25.
 */
public interface ExtendSystemConfigMapper extends ConditionMapper<SystemConfigVo, SystemConfigCondition> {
}

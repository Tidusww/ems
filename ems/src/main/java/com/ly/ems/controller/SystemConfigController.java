package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.config.SystemConfig;
import com.ly.ems.model.config.SystemConfigCondition;
import com.ly.ems.model.config.SystemConfigVo;
import com.ly.ems.service.config.SystemConfigService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tidus on 2017/10/24.
 */
@Controller
@RequestMapping(value = "/system", name = "系统设置")
public class SystemConfigController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfigController.class);
    @Autowired
    private CacheManager ehcacheManager;

    @Autowired
    private SystemConfigService systemConfigService;



    @ResponseBody
    @RequestMapping(value = "/config/getConfigs", name = "分页查询系统参数")
    public AjaxResult getEmployees(SystemConfigCondition conditions) {
        PageableResult<SystemConfigVo> pageableResult = systemConfigService.getConfigs(conditions);
        return AjaxResult.success(pageableResult);
    }

    @ResponseBody
    @RequestMapping(value = "/config/saveConfig", method = RequestMethod.POST, name = "更新系统参数")
    public AjaxResult saveSystemConfig(SystemConfig systemConfig) {
        systemConfigService.saveSystemConfig(systemConfig);
        return AjaxResult.success("更新系统参数成功");
    }


    @ResponseBody
    @RequestMapping(value = "/refreshConditionCache", method = RequestMethod.POST, name = "刷新静态条件缓存")
    public AjaxResult refreshConditionCache(HttpServletRequest request, Model model) {
        Cache cache = ehcacheManager.getCache("conditionCache");
        if (cache != null) {
            cache.flush();
        }
        return AjaxResult.success("刷新成功");

    }

    @ResponseBody
    @RequestMapping(value = "/refreshSystemConfigCache", method = RequestMethod.POST, name = "刷新系统配置缓存")
    public AjaxResult refreshSystemConfigCache(HttpServletRequest request, Model model) {
        Cache cache = ehcacheManager.getCache("systemConfigCache");
        if (cache != null) {
            cache.flush();
        }
        return AjaxResult.success("刷新成功");

    }
}

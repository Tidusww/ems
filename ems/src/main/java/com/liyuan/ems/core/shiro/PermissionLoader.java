package com.liyuan.ems.core.shiro;

import com.liyuan.ems.model.admin.Permission;
import com.liyuan.ems.model.admin.constant.StatusEnum;
import com.liyuan.ems.service.AdminService;
import org.apache.shiro.config.Ini;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PermissionLoader implements FactoryBean<Ini.Section> {

    @Autowired
    private AdminService adminService;

    private String filterChainDefinitions;

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Ini.Section getObject() throws Exception {

        List<PermissionFilterChain> filterChains = new ArrayList<PermissionFilterChain>();

        //加载配置文件中的权限配置
        Ini configIni = new Ini();
        configIni.load(filterChainDefinitions);
        Ini.Section section = configIni.getSection(Ini.DEFAULT_SECTION_NAME);

        for (Map.Entry<String, String> entry : section.entrySet()) {
            filterChains.add(new PermissionFilterChain(entry.getKey(), entry.getValue()));
        }

        //动态加载数据库中的权限配置
        List<Permission> perms = adminService.getAllPermissions(StatusEnum.ACTIVED);
        if (perms != null) {
            for (Permission p : perms) {
                filterChains.add(new PermissionFilterChain(p.getPermissionUrlPattern(), p.generatePerms()));
            }
        }

        //排序
        Collections.sort(filterChains);
        Collections.reverse(filterChains);

        Ini.Section finalSection = new Ini().addSection(Ini.DEFAULT_SECTION_NAME);
        for (PermissionFilterChain filterChain : filterChains) {
            if (finalSection.containsKey(filterChain.permissionUrl)) {//由于重复了url，因此后面的权限控制等级较为低，所以不需要忽略低等级
                continue;
            }
            finalSection.put(filterChain.permissionUrl, filterChain.permissionPattern);
        }
        return finalSection;
    }

    @Override
    public Class<?> getObjectType() {
        return Ini.Section.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

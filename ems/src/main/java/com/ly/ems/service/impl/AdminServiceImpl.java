package com.ly.ems.service.impl;

import com.ly.ems.dao.admin.AdminMapper;
import com.ly.ems.dao.admin.MenuMapper;
import com.ly.ems.model.admin.Menu;
import com.ly.ems.model.admin.Permission;
import com.ly.ems.model.admin.Role;
import com.ly.ems.model.admin.User;
import com.ly.ems.model.common.constant.StatusEnum;
import com.ly.ems.service.AdminService;
import com.ly.ems.dao.admin.MenuMapper;
import com.ly.ems.model.admin.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    MenuMapper menuMapper;

    @Override
    public List<Permission> getAllPermissions(StatusEnum status) {
        return adminMapper.getAllPermissions(status);
    }

    @Override
    public User authentication(String username, String password) {
        return adminMapper.getUserByUsernameAndPassword(username, password);
    }

    @Override
    public User getUserByUsername(String username) {
        return adminMapper.getUserByUsername(username);
    }

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return adminMapper.getRolesByUserId(userId);
    }

    @Override
    public List<Permission> getPermissionByRoleId(Integer roleId) {
        return adminMapper.getPermissionByRoleId(roleId);
    }

    @Override
    public List<Menu> getAllMenusByUsername(String username) {
        return this.getMenusByUsernameAndParentMenuId(username, ROOT_PARENT_MENU_ID);
    }

    @Override
    public List<Menu> getMenusByUsernameAndParentMenuId(String username, Integer parentMenuId) {
        List<Menu> menus = null;
        if (StringUtils.isEmpty(username)) {
            menus = menuMapper.getMenusByUsernameAndParentMenuId(username, parentMenuId);
        } else {
            menus = menuMapper.getMenusByUsernameAndParentMenuId(username, parentMenuId);
        }

        if (menus != null && !menus.isEmpty()) {
            for (Menu m : menus) {
                m.setSubMenus(this.getMenusByUsernameAndParentMenuId(username, m.getId()));
            }
        }
        return menus;
    }

    @Override
    public Map<String, String> mapMenuPathAndComponent(List<Menu> menus) {
        Map<String, String> map = new HashMap<String, String>();
        if (menus != null && !menus.isEmpty()) {
            for (Menu menu : menus) {
                if (StringUtils.isNotEmpty(menu.getComponentName())) {
                    map.put(menu.getRouteUrl(), menu.getComponentName());
                }
                map.putAll(this.mapMenuPathAndComponent(menu.getSubMenus()));
            }
        }
        return map;
    }
}
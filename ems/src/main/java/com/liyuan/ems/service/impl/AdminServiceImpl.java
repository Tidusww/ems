package com.liyuan.ems.service.impl;

import com.liyuan.ems.dao.admin.AdminDAO;
import com.liyuan.ems.dao.admin.MenuDAO;
import com.liyuan.ems.model.admin.Menu;
import com.liyuan.ems.model.admin.Permission;
import com.liyuan.ems.model.admin.Role;
import com.liyuan.ems.model.admin.User;
import com.liyuan.ems.model.admin.constant.StatusEnum;
import com.liyuan.ems.service.AdminService;
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
    AdminDAO adminDAO;

    @Autowired
    MenuDAO menuDAO;

    @Override
    public List<Permission> getAllPermissions(StatusEnum status) {
        return adminDAO.getAllPermissions(status);
    }

    @Override
    public User authentication(String username, String password) {
        return adminDAO.getUserByUsernameAndPassword(username, password);
    }

    @Override
    public User getUserByUsername(String username) {
        return adminDAO.getUserByUsername(username);
    }

    @Override
    public List<Role> getRolesByUserId(Integer userId) {
        return adminDAO.getRolesByUserId(userId);
    }

    @Override
    public List<Permission> getPermissionByRoleId(Integer roleId) {
        return adminDAO.getPermissionByRoleId(roleId);
    }

    @Override
    public List<Menu> getAllMenusByUsername(String username) {
        return this.getMenusByUsernameAndParentMenuId(username, ROOT_PARENT_MENU_ID);
    }

    @Override
    public List<Menu> getMenusByUsernameAndParentMenuId(String username, Integer parentMenuId) {
        List<Menu> menus = null;
        if (StringUtils.isEmpty(username)) {
            menus = menuDAO.getMenusByUsernameAndParentMenuId(username, parentMenuId);
        } else {
            menus = menuDAO.getMenusByUsernameAndParentMenuId(username, parentMenuId);
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
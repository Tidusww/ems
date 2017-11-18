package com.liyuan.ems.service.impl;

import com.liyuan.ems.dao.admin.AdminDAO;
import com.liyuan.ems.model.admin.Permission;
import com.liyuan.ems.model.admin.Role;
import com.liyuan.ems.model.admin.User;
import com.liyuan.ems.model.admin.constant.StatusEnum;
import com.liyuan.ems.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    AdminDAO adminDAO;

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
}

package com.liyuan.ems.service;


import com.liyuan.ems.core.datasource.DetermineDataSource;
import com.liyuan.ems.core.datasource.MultipleRoutingDataSource;
import com.liyuan.ems.model.admin.Permission;
import com.liyuan.ems.model.admin.Role;
import com.liyuan.ems.model.admin.User;
import com.liyuan.ems.model.admin.constant.StatusEnum;

import java.util.List;


/**
 * 系统用户权限相关
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface AdminService {
    public static final int LEVEL_ONE_MENU_PARENT_MENU_ID=0;

    /**
     * 获取所有Permission配置, 用于动态加载shiro权限
     * @return
     */
    List<Permission> getAllPermissions(StatusEnum status);

    /**
     * 根据用户名与密码校验账号
     */
    User authentication(String username, String password);

    /**
     * 根据用户名获取用户
     * @return
     */
    User getUserByUsername(String username);


    /**
     * 查询指定user的roles
     * @param userId
     * @return
     */
    List<Role> getRolesByUserId(Integer userId);

    /**
     * 查询指定role的permissions
     * @param roleId
     * @return
     */
    List<Permission> getPermissionByRoleId(Integer roleId);

}

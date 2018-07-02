package com.ly.ems.service.admin;


import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.core.datasource.MultipleRoutingDataSource;
import com.ly.ems.model.admin.Menu;
import com.ly.ems.model.admin.Permission;
import com.ly.ems.model.admin.Role;
import com.ly.ems.model.admin.User;
import com.ly.ems.model.common.constant.StatusEnum;
import com.ly.ems.core.datasource.DetermineDataSource;
import com.ly.ems.model.admin.User;

import java.util.List;
import java.util.Map;


/**
 * 系统用户权限相关
 */
@DetermineDataSource(MultipleRoutingDataSource.DATA_SOURCE_EMS)
public interface AdminService {
    int ROOT_PARENT_MENU_ID = 0;

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


    /**
     * 根据用户名查询所有菜单
     * @param username
     * @return
     */
    List<Menu> getAllMenusByUsername(String username);
    /**
     * 根据用户名查询父菜单下的子菜单
     * @param username
     * @param parentMenuId
     * @return
     */
    List<Menu> getMenusByUsernameAndParentMenuId(String username, Integer parentMenuId);
    /**
     * 为了前端的映射
     * @param menus
     * @return
     */
    Map<String, String> mapMenuPathAndComponent(List<Menu> menus);

}

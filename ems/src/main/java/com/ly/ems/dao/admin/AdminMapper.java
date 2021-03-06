package com.ly.ems.dao.admin;

import com.ly.ems.model.admin.User;
import org.apache.ibatis.annotations.Param;
import com.ly.ems.model.admin.Permission;
import com.ly.ems.model.admin.Role;
import com.ly.ems.model.admin.User;
import com.ly.ems.model.common.constant.StatusEnum;

import java.util.List;

/**
 * Created by tidus on 2017/11/8.
 */
public interface AdminMapper {

    /**
     * 获取所有Permission配置, 用于动态加载shiro权限
     * @return
     */
    List<Permission> getAllPermissions(@Param("status") StatusEnum status);

    /**
     * 根据用户名与密码校验账号
     */
    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名获取用户
     * @return
     */
    User getUserByUsername(@Param("username") String username);


    /**
     * 查询指定user的roles
     * @param userId
     * @return
     */
    List<Role> getRolesByUserId(@Param("userId") Integer userId);

    /**
     * 查询指定role的permissions
     * @param roleId
     * @return
     */
    List<Permission> getPermissionByRoleId(@Param("roleId") Integer roleId);
}

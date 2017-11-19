package com.liyuan.ems.core.shiro;

import com.liyuan.ems.common.utils.MD5Util;
import com.liyuan.ems.model.admin.Permission;
import com.liyuan.ems.model.admin.Role;
import com.liyuan.ems.model.admin.User;
import com.liyuan.ems.model.common.constant.StatusEnum;
import com.liyuan.ems.service.AdminService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by tidus on 2017/10/30.
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AdminService adminService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String username = String.valueOf(principals.getPrimaryPrincipal());
        User user = adminService.getUserByUsername(username);

        List<Role> roles = adminService.getRolesByUserId(user.getId());
        for(Role role : roles) {
            authorizationInfo.addRole(String.valueOf(role.getId()));
            List<Permission> permissions = adminService.getPermissionByRoleId(role.getId());
            for(Permission permission : permissions) {
                authorizationInfo.addStringPermission(String.valueOf(permission.getId()));
            }

        }

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = String.valueOf(authenticationToken.getPrincipal());
        String password = String.valueOf((char[])authenticationToken.getCredentials());
        String passwordMD5 = "";

        passwordMD5 = MD5Util.encode2hex(password);

        User user = adminService.authentication(username, passwordMD5);

        if (user == null) {
            throw new AuthenticationException("用户名或密码错误");
        }

        if (user.getStatus() != StatusEnum.ACTIVED) {
            throw new AuthenticationException(String.format("账号状态无效：%s", user.getStatus().toString()));
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, getName());
        return authenticationInfo;
    }
}

package com.ly.ems.core.shiro;

/**
 * 用于动态加载shiro权限FilterChainDifinitions时, 方便排序
 */
public class PermissionFilterChain implements Comparable<PermissionFilterChain> {
    public String permissionUrl;
    public String permissionPattern;
    private ShiroLevel shiroLevel;

    public PermissionFilterChain(String permissionUrl, String permissionPattern) {
        if(permissionUrl == null || permissionPattern == null){
            throw new NullPointerException();
        }
        this.permissionUrl = permissionUrl;
        this.permissionPattern = permissionPattern;
        this.shiroLevel = ShiroLevel.parse(permissionPattern);
    }

    @Override
    public int compareTo(PermissionFilterChain o) {
        if(this == null){
            if(o != null){
                return -1;
            }else{
                return 0;
            }
        }
        if(o == null){
            return 1;
        }
        if(this.shiroLevel.ordinal() < o.shiroLevel.ordinal()){
            return -1;
        }else if (this.shiroLevel.ordinal() > o.shiroLevel.ordinal()){
            return 1;
        }
        return this.permissionUrl.compareTo(o.permissionUrl);
    }

    private enum ShiroLevel {
        SESSION, AUTHC, ANON, PERMS, ROLES;

        public static ShiroLevel parse(String perms){
            if(perms!=null) {
                if (perms.startsWith("session")){
                    return SESSION;
                }else if (perms.startsWith("authc")){
                    return AUTHC;
                }else if(perms.startsWith("perms")){
                    return PERMS;
                }else if(perms.startsWith("roles")){
                    return ROLES;
                }
            }
            return ANON;
        }
    }
}

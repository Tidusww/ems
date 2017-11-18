package com.liyuan.ems.model.admin;

import com.liyuan.ems.model.admin.constant.StatusEnum;

import java.util.List;

public class Permission {
    private Integer id;
    private String permissionName;
    private String permissionUrlPattern;
    private StatusEnum status;

    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionUrlPattern() {
        return permissionUrlPattern;
    }

    public void setPermissionUrlPattern(String permissionUrlPattern) {
        this.permissionUrlPattern = permissionUrlPattern;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }



    public String generatePerms(){
        return String.format("perms[%d]", this.id);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return that.permissionUrlPattern.equals(this.permissionUrlPattern);
    }

    @Override
    public int hashCode() {
        return this.permissionUrlPattern.hashCode();
    }
}

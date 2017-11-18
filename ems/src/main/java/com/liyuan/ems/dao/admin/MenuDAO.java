package com.liyuan.ems.dao.admin;

import com.liyuan.ems.model.admin.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MenuDAO {

    /**
     * 根据用户名查询父菜单下的子菜单
     * @param username
     * @param parentMenuId
     * @return
     */
    List<Menu> getMenusByUsernameAndParentMenuId(@Param("username") String username , @Param("parentMenuId") Integer parentMenuId);

}

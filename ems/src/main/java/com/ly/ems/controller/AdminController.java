package com.ly.ems.controller;

import javax.servlet.http.HttpSession;
import javax.validation.*;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.core.exception.EMSBaseException;
import com.ly.ems.core.springmvc.controller.AbstractBaseController;
import com.ly.ems.model.admin.Menu;
import com.ly.ems.model.admin.User;
import com.ly.ems.service.AdminService;
import com.ly.ems.model.admin.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 登陆\权限\菜单相关的Controller
 * Created by tidus on 2017/10/31.
 */
@Controller
public class AdminController extends AbstractBaseController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;


    @RequestMapping(value = "/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        clearLoginUser(request);
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            currentUser.logout();
        }
        return "redirect:/login";
    }


    @RequestMapping(value = "/loginRedirect")
    public String loginRedirect(HttpServletRequest request, Model model) {
        Subject subject = SecurityUtils.getSubject();
        // 已登陆则 跳到首页
        if (subject.isAuthenticated()) {
            return "redirect:/index";
        } else {
            return "redirect:/logout";
        }
    }

    @ResponseBody
    @PostMapping(value = "/doLogin")
    public AjaxResult doLogin(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
        try {

            Subject subject = SecurityUtils.getSubject();
            if (result.hasErrors()) {
                return AjaxResult.fail("用户名密码参数有误!");
            }

            if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
                return AjaxResult.fail("用户名或密码不能为空!");
            }

            //登陆
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(token);

            //保存登陆信息
            User authUser = adminService.getUserByUsername(user.getUsername());
            setLoginUser(request, authUser);

        } catch (AuthenticationException e) {
            logger.error(e.toString());
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        } catch (EMSBaseException e) {
            logger.error(e.toString());
            e.printStackTrace();
            return AjaxResult.fail(e.getMessage());
        }

        return AjaxResult.success("登陆成功");
    }



    @RequestMapping(value = "/menu/getUserMenus")
    @ResponseBody
    private AjaxResult getUserMenus() {

        Subject currentUser = SecurityUtils.getSubject();
        String username = String.valueOf(currentUser.getPrincipal());

        List<Menu> menus = adminService.getAllMenusByUsername(username);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("menus", menus);
        data.put("routeAndComponent", adminService.mapMenuPathAndComponent(menus));

        return AjaxResult.success(data);
    }
}

package com.liyuan.ems.controller;

import javax.servlet.http.HttpSession;
import javax.validation.*;

import com.liyuan.ems.common.utils.AjaxResult;
import com.liyuan.ems.core.exception.EMSBaseException;
import com.liyuan.ems.core.springmvc.controller.AbstractBaseController;
import com.liyuan.ems.model.admin.User;
import com.liyuan.ems.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
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
}

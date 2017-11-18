package com.liyuan.ems.controller;

import com.liyuan.ems.core.springmvc.controller.AbstractBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by tidus on 2017/11/12.
 */
@Controller
public class PageController extends AbstractBaseController {

    private Logger logger = LoggerFactory.getLogger(PageController.class);

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, Model model) {
        return "login";
    }

    @RequestMapping(value="/index")
    public String index(HttpServletRequest request, Model model) {
        return "index";
    }


}

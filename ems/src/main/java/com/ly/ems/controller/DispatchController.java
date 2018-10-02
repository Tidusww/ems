package com.ly.ems.controller;

import com.ly.ems.common.utils.AjaxResult;
import com.ly.ems.model.common.PageableResult;
import com.ly.ems.model.dispatch.DispatchRelCondition;
import com.ly.ems.model.dispatch.DispatchRelVo;
import com.ly.ems.service.dispatch.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tidus on 2018/9/26.
 */
@Controller
@RequestMapping(value = "/dispatch", name = "派遣信息管理")
public class DispatchController {

    @Autowired
    DispatchService dispatchService;

    @ResponseBody
    @GetMapping(value = "/list", name = "分页查询派遣信息")
    public AjaxResult getDispatchRels(DispatchRelCondition conditions) {
        PageableResult<DispatchRelVo> pageableResult = dispatchService.getDispatchRelsByConditions(conditions);
        return AjaxResult.success(pageableResult);
    }

}

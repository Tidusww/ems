package com.ly.ems.controller;

import com.ly.ems.common.utils.file.DownloadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class ExportController {
    private Logger LOGGER = LoggerFactory.getLogger(ExportController.class);

    @RequestMapping(("/export"))
    public void export(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) {
        DownloadUtil.downloadFile(filePath, fileName);
    }
}

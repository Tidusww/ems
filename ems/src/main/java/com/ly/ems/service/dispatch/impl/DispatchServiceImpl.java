package com.ly.ems.service.dispatch.impl;

import com.ly.ems.dao.dispatch.DispatchRelMapper;
import com.ly.ems.service.dispatch.DispatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tidus on 2018/1/15.
 */
@Service
public class DispatchServiceImpl implements DispatchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispatchServiceImpl.class);

    @Autowired
    DispatchRelMapper dispatchRelMapper;






}

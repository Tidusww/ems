package com.ly.ems.dao.dispatch;

import com.ly.ems.model.dispatch.DispatchRel;
import com.ly.ems.model.dispatch.DispatchRelExample;
import java.util.List;
import tk.mybatis.mapper.common.BaseMapper;

public interface DispatchRelMapper extends BaseMapper<DispatchRel> {
    List<DispatchRel> selectByExample(DispatchRelExample example);
}
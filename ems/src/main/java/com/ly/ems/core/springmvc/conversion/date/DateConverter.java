package com.ly.ems.core.springmvc.conversion.date;

import com.zy.ossadmin.common.utils.DateUtil;
import com.zy.ossadmin.exception.BusinessException;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * Created by tidus on 2017/11/29.
 */
public class DateConverter<T extends Date> implements Converter<String, T> {


    @Override
    public T convert(String source) {

        try {
            Date date = DateUtil.parseDate(source);
            return (T) date;
        } catch (BusinessException e) {
            e.printStackTrace();
        }

        return null;
    }
}

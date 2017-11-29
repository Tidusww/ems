package com.ly.ems.core.springmvc.conversion.date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Date;

/**
 * Created by tidus on 2017/11/29.
 */
public class DateConverterFactory implements ConverterFactory<String, Date> {

    @Override
    public <T extends Date> Converter<String, T> getConverter(Class<T> aClass) {
        return new DateConverter();
    }

}

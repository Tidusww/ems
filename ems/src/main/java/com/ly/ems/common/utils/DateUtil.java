package com.ly.ems.common.utils;


import com.ly.ems.core.exception.EMSRuntimeException;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by tidus on 2017/11/29.
 */
public class DateUtil {

    /**
     * 字符串转日期适配方法
     *
     * @param dateStr 日期字符串
     */
    public static Date parseDate(String dateStr) throws EMSRuntimeException {
        Date date = null;

        if (!StringUtils.isEmpty(dateStr)) {
            //判断是不是日期字符串，如Wed May 28 08:00:00 CST 2014
            if (dateStr.contains("CST")) {
                date = new Date(dateStr);
            } else {
                dateStr = dateStr.replace("年", " -").replace("月", " -").replace("日", "").replaceAll("/", "-").replaceAll("\\.", "-").trim();
                String fm = "";

                //确定日期格式
                if (Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}.*").matcher(dateStr).matches()) {
                    fm = "yyyy-MM-dd";
                } else if (Pattern.compile("^[0-9]{4}-[0-9]{2}.*").matcher(dateStr).matches()) {
                    fm = "yyyy-MM";
                } else if (Pattern.compile("^[0-9]{4}-[0-9]-[0-9]+.*||^[0-9]{4}-[0-9]+-[0-9].*").matcher(dateStr).matches()) {
                    fm = "yyyy-M-d";
                } else if (Pattern.compile("^[0-9]{2}-[0-9]{2}-[0-9]{2}.*").matcher(dateStr).matches()) {
                    fm = "yy-MM-dd";
                } else if (Pattern.compile("^[0-9]{2}-[0-9]-[0-9]+.*||^[0-9]{2}-[0-9]+-[0-9].*").matcher(dateStr).matches()) {
                    fm = "yy-M-d";
                }


                //确定时间格式
                if (Pattern.compile(".*[ ][0-9]{2}").matcher(dateStr).matches()) {
                    fm += " HH";
                } else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}").matcher(dateStr).matches()) {
                    fm += " HH:mm";
                } else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}:[0-9]{2}").matcher(dateStr).matches()) {
                    fm += " HH:mm:ss";
                } else if (Pattern.compile(".*[ ][0-9]{2}:[0-9]{2}:[0-9]{2}:[0-9]{0,3}").matcher(dateStr).matches()) {
                    fm += " HH:mm:ss:sss";
                }

                if (!"".equals(fm)) {
                    try {
                        date = new SimpleDateFormat(fm).parse(dateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        throw new EMSRuntimeException("字符串参数 " + dateStr + " 无法被转换为日期格式！匹配格式:"+fm);
                    }
                }
            }
        }
        return date;
    }
}

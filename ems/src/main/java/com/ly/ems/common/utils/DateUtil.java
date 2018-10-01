package com.ly.ems.common.utils;


import com.ly.ems.core.exception.EMSRuntimeException;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by tidus on 2017/11/29.
 */
public class DateUtil {

    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

    public static final String[] WEEK_NAMES = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};


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
                        throw new EMSRuntimeException("字符串参数 " + dateStr + " 无法被转换为日期格式！匹配格式:" + fm);
                    }
                }
            }
        }
        return date;
    }


    /**
     * 根据时间获取周几
     * 0: 周日
     * 1~6: 周一~周六
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 返回几号
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据时间获取周几
     *
     * @param date
     * @return
     */
    public static String getWeekName(Date date) {
        if (date == null) {
            return null;
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return WEEK_NAMES[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回日期所在月份的第一天和最后一天
     *
     * @param date
     * @return
     */
    public static Date[] getMonthBoundary(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // 第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = calendar.getTime();

        // 最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date endDate = calendar.getTime();

        return new Date[]{startDate, endDate};
    }

    /**
     * 下一天
     * @param date
     * @return
     */
    public static Date nextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }


    public static void main(String[] args) {

        Date[] dates = getMonthBoundary(new Date());
        System.out.println(dates[0]);
        System.out.println(dates[1]);


        System.out.println(String.format("今天是%d号", getDayOfMonth(new Date())));

    }
}

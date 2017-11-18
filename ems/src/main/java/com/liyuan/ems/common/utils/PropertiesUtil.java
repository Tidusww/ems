package com.zy.ossadmin.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static String filePath = "";//文件路径

    public static String getSSOProperties(String key){
        filePath = "sso.properties";
        return getValue(key, filePath);
    }

    public static String getDBProperties(String key) {
        filePath = "dataSource.properties";
        return getValue(key, filePath);
    }

    public static String getAppProperties(String key) {
        filePath = "application.properties";
        return getValue(key, filePath);
    }

    /**
     * 获取指定路径下property文件中的某个字段(没有默认值)
     *
     * @param key      字段
     * @param filePath 属性文件
     * @return 与key对应的value
     * @throws FileNotFoundException if property file doesn't exists
     * @throws IOException           if there is some exception when load from property file
     */

    private static String getValue(String key, String filePath) {
        InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
        Properties props = new Properties();
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String value = props.getProperty(key);
        return value;
    }

    public static String getSSOBaseUrl(){
        return getSSOProperties("ssoBaseUrl");
    }

}

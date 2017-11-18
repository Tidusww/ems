package com.liyuan.ems.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by tidus on 2017/8/22.
 */
public class MultipleRoutingDataSource extends AbstractRoutingDataSource {

    public static final String DATA_SOURCE_EMS = "DATA_SOURCE_EMS";

    private static final ThreadLocal<String> dataSourceTypes = new InheritableThreadLocal<String>(){
        @Override
        protected String initialValue(){
            return DATA_SOURCE_EMS;
        }
    };

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceTypes.get();
    }

    public static void setDataSourceKey(String dataSource){
        dataSourceTypes.set(dataSource);
    }
    public static String getCurrentDataSourceKey(){
        return dataSourceTypes.get();
    }
}

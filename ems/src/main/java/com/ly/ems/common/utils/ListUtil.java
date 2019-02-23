package com.ly.ems.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tidus on 2019-02-23 in ems.
 */
public class ListUtil {

    /**
     * 子集
     *
     * @param list
     * @param pageIndex 从0开始
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<T> subList(List<T> list, int pageIndex, int pageSize) {
        int formIndex = pageIndex * pageSize;
        if(formIndex >= list.size()) {
            return null;
        }
        int toIndex = (pageIndex + 1) * pageSize;
        if (toIndex > list.size()) {
            toIndex = list.size();
        }
        return list.subList(formIndex, toIndex);
    }

}

package com.wangjp.sell.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/4/17 8:15 下午
 * @detail
 */
public class CopyUtil {

    private static String[] ignoreList = { "createTime", "updateTime" };

    public static String[] getIgnoreKey() {
        return ignoreList;
    }

    public static String[] getIgnoreKey(String key) {
        List<String> list = Arrays.asList(ignoreList);
        list.add(key);
        return list.toArray(new String[0]);
    }

    public static String[] getIgnoreKey(String[] array) {
        List<String> list = Arrays.asList(ignoreList);
        list.addAll(Arrays.asList(array));
        return list.toArray(new String[0]);
    }
}

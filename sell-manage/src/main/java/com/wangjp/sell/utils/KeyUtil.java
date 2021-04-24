package com.wangjp.sell.utils;

import java.util.Random;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/3/6 6:23 下午
 * @detail
 */
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * @return 随机 key
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();

        // 100000 到 999999 的随机整数
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}

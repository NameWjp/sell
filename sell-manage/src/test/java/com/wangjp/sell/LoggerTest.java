package com.wangjp.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/1/25 9:28 下午
 * @detail
 */
@SpringBootTest
@Slf4j
public class LoggerTest {

    @Test
    public void test1() {
        log.debug("debug..");
        log.info("info..");
        log.warn("warn..");
        log.error("error..");
    }
}

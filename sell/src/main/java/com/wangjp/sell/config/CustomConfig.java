package com.wangjp.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/8/24 9:44 下午
 * @detail 自定义过滤请求配置
 */
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {
    // 不需要拦截的地址
    private IgnoreConfig ignores;
}

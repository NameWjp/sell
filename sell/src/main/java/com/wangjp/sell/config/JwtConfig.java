package com.wangjp.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/9/27 17:08
 * @detail
 */
@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtConfig {

    // JWT 加解密使用的密钥
    private String secret;

    // 过期时间
    private Long expiration;

    // jwt存储的请求头
    private String tokenHeader;

    // jwt 负载前缀
    private String tokenPrefix;
}

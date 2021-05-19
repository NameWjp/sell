package com.wangjp.sell.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/5/19 11:28 下午
 * @detail
 */
@Configuration
// 开启 jpa 审计功能（用于自动添加时间等）
@EnableJpaAuditing
public class JpaConfiguration {
}

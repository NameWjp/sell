package com.wangjp.sell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// 开启 jpa 审计功能（用于自动添加时间等）
@EnableJpaAuditing
public class SellApplication {
    public static void main(String[] args) {
        SpringApplication.run(SellApplication.class, args);
    }

}

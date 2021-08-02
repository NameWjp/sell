package com.wangjp.sell.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/29 9:43 下午
 * @detail
 */
@Configuration
// 开启 security
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭 csrf 防御，否则前端需要携带自动生产的 token
                .csrf().disable()
                // 关闭不能通过 iframe 引用的限制
                .headers().frameOptions().disable()
                .and();
        http
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                // 其他接口需要登录后才能访问
                .anyRequest()
                .authenticated()
                .and();
    }

    /*
     * 注入 BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

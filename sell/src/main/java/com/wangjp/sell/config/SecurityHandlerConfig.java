package com.wangjp.sell.config;

import com.wangjp.sell.utils.ResponseUtil;
import com.wangjp.sell.utils.ResultVOUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/9/4 7:32 下午
 * @detail spring-security 结果处理类
 */
@Configuration
public class SecurityHandlerConfig {

    // 已登录但是没有权限的异常
    @Bean
    public AccessDeniedHandler accessDeniedException() {
        return (httpServletRequest, httpServletResponse, e) -> ResponseUtil.renderJson(httpServletResponse, ResultVOUtil.error(403, "没有访问权限"));
    }

    // 匿名访问时的异常
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (httpServletRequest, httpServletResponse, e) -> ResponseUtil.renderJson(httpServletResponse, ResultVOUtil.error(403, "没有访问权限"));
    }
}

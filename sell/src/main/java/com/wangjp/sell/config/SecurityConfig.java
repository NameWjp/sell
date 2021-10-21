package com.wangjp.sell.config;

import com.wangjp.sell.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/6/29 9:43 下午
 * @detail
 */
@Configuration
// 开启 security
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // 手动注入 AuthenticationManager 用于登录注册
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 开启跨域
                .cors().and()
                // 关闭 csrf 防御，否则前端需要携带自动生产的 token
                .csrf().disable()
                // 关闭不能通过 iframe 引用的限制
                .headers().frameOptions().disable().and()
                // 登录行为自定义
                .formLogin().disable()
                .httpBasic().disable()

                // 认证请求
                .authorizeRequests()
                // swagger放行
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/**")
                .permitAll()
                // 上传图片方向
                .antMatchers("/upload/**")
                .permitAll()
                // 登录接口放行
                .antMatchers("/auth/login")
                .permitAll()
                // 公共接口放行
                .antMatchers("/common/**")
                .permitAll()
                //其他都需要登录访问
                .anyRequest().authenticated()
                .and()

                // 登出行为自定义
                .logout().disable()
                // 关闭默认 session 管理
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // 异常处理
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);

        // 添加自定义的 jwt 过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*
     * 注入 BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

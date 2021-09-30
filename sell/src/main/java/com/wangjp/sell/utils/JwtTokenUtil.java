package com.wangjp.sell.utils;

import cn.hutool.core.util.StrUtil;
import com.wangjp.sell.config.JwtConfig;
import com.wangjp.sell.constant.RedisConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/9/27 17:14
 * @detail jwt 工具类
 */
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Component
public class JwtTokenUtil {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取 jwt token
     */
    public String createJwt(UserDetails userDetails) {
        SecretKey secretKey = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS512.getJcaName());
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(secretKey)
                .compact();

        // 生成 redis 用于控制 jwt 的有效时间
        redisTemplate.opsForValue().set(getRedisKeyFromToken(token), userDetails, jwtConfig.getExpiration(), TimeUnit.SECONDS);

        return token;
    }

    /**
     * 从 token 中获取 JWT 中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;

        // 验证 jwt 的有效时间
        UserDetails userDetails = (UserDetails) redisTemplate.opsForValue().get(getRedisKeyFromToken(token));
        if (userDetails == null) {
            throw new RuntimeException();
        }

        try {
            SecretKey secretKey = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS512.getJcaName());
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            System.out.println("JWT格式验证失败: " + token);
        }

        return claims;
    }

    /**
     * 生成 token 的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtConfig.getExpiration() * 1000);
    }

    /**
     * 根据 token 获取 redisKey
     */
    private String getRedisKeyFromToken(String token) {
        return RedisConstant.userCache + token;
    }

    /**
     * 从 token 中获取过期时间
     */
    public Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据 token 获取用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }

        return username;
    }

    /**
     * 从请求中获取 token
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtConfig.getTokenHeader());
        if (StrUtil.isBlank(bearerToken)) {
            bearerToken = request.getParameter(jwtConfig.getTokenHeader());
        }

        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(jwtConfig.getTokenPrefix())) {
            return bearerToken.substring(jwtConfig.getTokenPrefix().length()).trim();
        }
        return null;
    }
}

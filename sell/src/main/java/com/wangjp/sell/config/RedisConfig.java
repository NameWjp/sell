package com.wangjp.sell.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author wangjp
 * @email 943375372@qq.com
 * @date 2021/10/9 10:07
 * @detail redis 配置，解决默认的 byte 序列化无法查看的问题
 */

@Configuration
public class RedisConfig {

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用 Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field，get 和 set。以及修饰符范围，ANY是都有包括 private 和 public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 这一句非常的重要，作用是序列化时将对象全类名一起保存下来，不然无法直接反序列化
        // 但这样有一个缺陷，如果类的位置变化了会导致无法反序列化，考虑到 redis 储存的信息不太重要，为了方便暂时这样做
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置 key 和 value 的序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 调用后完成设置
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}

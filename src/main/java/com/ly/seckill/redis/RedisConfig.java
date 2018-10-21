package com.ly.seckill.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return om;
    }

    /**
     * Jackson2JsonRedisSerializer和GenericJackson2JsonRedisSerializer的区别
     * https://blog.csdn.net/bai_bug/article/details/81222519
     */
//    @Bean
//    public GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer(ObjectMapper om) {
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(om);
//        return serializer;
//    }
    @Bean
    public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer(ObjectMapper om) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                       RedisSerializer jackson2JsonRedisSerializer) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(stringSerializer);//key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }


    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append("." + method.getName() + "(");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            sb.append(")");
            return sb.toString();
        };
    }

    /**
     * springboot2.x中，RedisCacheManager已经没有了单参数的构造方法
     * 1.x中通过参数redisTemplate配置的方式不可行
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisSerializer jackson2JsonRedisSerializer) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))  // 设置缓存有效期一小时
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> "yourAppName".concat(":").concat(cacheName).concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration)
                .build();
    }
}

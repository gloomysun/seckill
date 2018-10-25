package com.ly.seckill.config;

import com.ly.seckill.utils.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeckillConfig {
    @Bean
    public SnowFlake snowFlake() {
        return new SnowFlake(0, 0);
    }
}

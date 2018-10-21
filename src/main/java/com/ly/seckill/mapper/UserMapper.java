package com.ly.seckill.mapper;

import com.ly.seckill.domain.User;
import org.springframework.cache.annotation.Cacheable;

public interface UserMapper {
    @Cacheable("cache1")//Cache是发生在cache1上的
    User getUserById(int id);
}

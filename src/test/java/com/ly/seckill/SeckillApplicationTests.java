package com.ly.seckill;

import com.ly.seckill.domain.User;
import com.ly.seckill.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillApplicationTests {

//    @Autowired
//    private RedisTemplate redisTemplate;
    @Autowired
    private UserMapper userMapper;

//    @Test
//    public void testReids() {
//        Map map = new HashMap<>();
//        map.put("a", "dd");
//        map.put("c", "ee");
//        redisTemplate.opsForValue().set("sss", map);
//    }

    @Test
    public void testCache(){
        User user = userMapper.getUserById(1);
        User user1 = userMapper.getUserById(1);
        System.err.println(user);
    }
    @Test
    public void contextLoads() {

    }

}

package com.ly.seckill.service;

import com.ly.seckill.aop.SysLog;
import com.ly.seckill.domain.User;
import com.ly.seckill.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @SysLog
    public User getUserById(long id) {
        return null;
    }

    public void insert(User user) {
        long id = userMapper.insert(user);
        System.out.println(id);
        System.out.println(user);
    }
}

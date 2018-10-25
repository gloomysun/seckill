package com.ly.seckill.service;

import com.ly.seckill.aop.SysLog;
import com.ly.seckill.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @SysLog
    public User getUserById(int id) {
        return new User(1, "liy");
    }
}

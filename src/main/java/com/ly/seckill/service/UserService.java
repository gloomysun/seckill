package com.ly.seckill.service;

import com.ly.seckill.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUserById(int id) {

        return new User(1, "liy");
    }
}

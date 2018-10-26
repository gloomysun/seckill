package com.ly.seckill.controller;

import com.ly.seckill.domain.User;
import com.ly.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("user/insert")
    public String insert() {
        User user = new User();
        user.setName("zhangsan");
        userService.insert(user);
        return null;
    }
}

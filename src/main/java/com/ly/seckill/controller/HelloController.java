package com.ly.seckill.controller;

import com.ly.seckill.domain.User;
import com.ly.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "liyang");
        return "hello";
    }
}

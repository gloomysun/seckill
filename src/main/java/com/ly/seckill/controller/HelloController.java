package com.ly.seckill.controller;

import com.ly.seckill.domain.User;
import com.ly.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class HelloController {
    @Autowired
    private UserService userService;


    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "liyang");
        return "hello";
    }

        @RequestMapping("/getuser")
    public User getUser() {
        return new User(1111, "liyang");
    }
}

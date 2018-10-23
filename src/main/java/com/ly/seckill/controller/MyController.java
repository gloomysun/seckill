package com.ly.seckill.controller;

import com.ly.seckill.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MyController {
    @RequestMapping("/haha")
    @ResponseBody
    public Map getUser(){
        Map map  = new HashMap();
        map.put("aa","bb");
        return map;
    }
}

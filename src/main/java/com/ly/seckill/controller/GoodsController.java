package com.ly.seckill.controller;

import com.ly.seckill.common.Constant;
import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/to_list")
    public String toList(@CookieValue("token") String token, Model model) {
        SeckillUser user = (SeckillUser) redisService.get(Constant.TOKEN_PREFIX, token);
        model.addAttribute("user", user);
        return "goods_list";
    }
}

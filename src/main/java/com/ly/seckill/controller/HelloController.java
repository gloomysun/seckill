package com.ly.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.ly.seckill.domain.User;
import com.ly.seckill.queue.rabbitmq.RabbitSender;
import com.ly.seckill.queue.rabbitmq.SeckillMsg;
import com.ly.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

@Controller
public class HelloController {
    @Autowired
    private UserService userService;
    @Autowired
    private RabbitSender sender;

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

    @RequestMapping("/mq")
    @ResponseBody
    public String sendmsg() {
        sender.send("nihaoa");
        return null;
    }

    @RequestMapping("/mq/work")
    @ResponseBody
    public String sendWork() {
        sender.sendWork("nihaoa");
        return null;
    }
    @RequestMapping("/mq/routing")
    @ResponseBody
    public String sendRouting() {
        sender.sendRouting("nihaoa");
        return null;
    }
    @RequestMapping("/mq/topic")
    @ResponseBody
    public String sendTopic() {
        sender.sendTopic("nihaoa");
        return null;
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public String sendFanout() {
        sender.sendFanout("nihaoa");
        return null;
    }
    @RequestMapping("/mq/seckill")
    @ResponseBody
    public String sendSeckill() {
        SeckillMsg seckillMsg = new SeckillMsg();
        seckillMsg.setUserId(100);
        seckillMsg.setSeckillId(10000);
        sender.sendSeckillMsg(JSON.toJSONString(seckillMsg));
        return null;
    }

}

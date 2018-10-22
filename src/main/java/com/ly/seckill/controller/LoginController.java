package com.ly.seckill.controller;

import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.result.CodeMsg;
import com.ly.seckill.result.SeckillResult;
import com.ly.seckill.service.SeckillUserService;
import com.ly.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SeckillUserService seckillUserService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public LoginVo doLogin(@Valid LoginVo loginVo) {
//        if(bindingResult.hasErrors()){
//            for(ObjectError error:bindingResult.getAllErrors()){
//                System.out.println(error.getDefaultMessage());
//            }
//        }
        return loginVo;

//        if(codeMsg.getCode()==0){
//            return SeckillResult.success(loginVo);
//        }else {
//            return SeckillResult.error(codeMsg);
//        }


    }
}

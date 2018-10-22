package com.ly.seckill.service;

import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.mapper.SeckillUserMapper;
import com.ly.seckill.result.CodeMsg;
import com.ly.seckill.utils.MD5Util;
import com.ly.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillUserService {

    @Autowired
    private SeckillUserMapper seckillUserMapper;
    public CodeMsg login(LoginVo loginVo){
        if(loginVo==null){
            return CodeMsg.SERVER_ERROR;
        }
        SeckillUser user = seckillUserMapper.getById(loginVo.getMobile());
        if(user==null){
            return CodeMsg.SERVER_ERROR;
        }
        if(user.getPassword()!= MD5Util.md5(loginVo.getPassword())){
            return CodeMsg.SERVER_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}

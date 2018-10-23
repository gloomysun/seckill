package com.ly.seckill.service;

import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.exception.GlobalException;
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

    public CodeMsg login(LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        SeckillUser user = seckillUserMapper.getById(loginVo.getMobile());
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String calcPass = MD5Util.formPassToDBPass(loginVo.getPassword(), user.getSalt());
        if (!dbPass.equals(calcPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        return CodeMsg.SUCCESS;
    }
}

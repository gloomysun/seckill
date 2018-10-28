package com.ly.seckill.service;

import com.ly.seckill.common.Constant;
import com.ly.seckill.domain.SeckillGoods;
import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.exception.GlobalException;
import com.ly.seckill.mapper.SeckillUserMapper;
import com.ly.seckill.redis.RedisService;
import com.ly.seckill.result.CodeMsg;
import com.ly.seckill.utils.MD5Util;
import com.ly.seckill.utils.UUIDUtil;
import com.ly.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class SeckillUserService  {

    @Autowired
    private SeckillUserMapper seckillUserMapper;
    @Autowired
    private RedisService redisService;


    public String login(HttpServletResponse response, LoginVo loginVo) {
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
        //生成token
        String token = UUIDUtil.uuid();
        //添加cookie
        addCookie(response, token, user);
        return token;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
        redisService.set(Constant.TOKEN_PREFIX, token, user, Constant.TOKEN_EXPIRE);
        Cookie cookie = new Cookie(Constant.COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(Constant.TOKEN_EXPIRE);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public Object getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SeckillUser seckillUser = (SeckillUser) redisService.get(Constant.TOKEN_PREFIX, token);
        if (seckillUser != null) {
            addCookie(response, token, seckillUser);
        }
        return seckillUser;
    }


}

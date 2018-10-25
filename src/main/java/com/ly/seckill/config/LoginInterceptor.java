package com.ly.seckill.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        response.sendRedirect("/login/to_login");
        // 如果返回false表示拦截不继续执行handler，如果返回true表示放行
        return true;
    }
}

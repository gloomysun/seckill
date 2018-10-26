package com.ly.seckill.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LogAspect {
    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.ly.seckill.aop.SysLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long starttime = System.currentTimeMillis();
        logger.info("=============================开始=======================");
        Object result = point.proceed();
        long endTime = System.currentTimeMillis();
        logger.info("=============================结束=======================");
        logger.info(String.format(point+"共耗时%sms", endTime - starttime));

        return result;
    }
}

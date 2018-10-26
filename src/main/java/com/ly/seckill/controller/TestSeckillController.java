package com.ly.seckill.controller;

import com.ly.seckill.exception.SeckillException;
import com.ly.seckill.result.SeckillResult;
import com.ly.seckill.service.GoodsService;
import com.ly.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/testSeckill")
public class TestSeckillController {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private GoodsService goodsService;

    //创建线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    private static CountDownLatch latch = new CountDownLatch(10000);
    private static Logger logger = LoggerFactory.getLogger(TestSeckillController.class);

    /**
     * 方案1：
     */
    @RequestMapping("/simpleSeckill")
    public SeckillResult simpleSeckill() throws InterruptedException {
        long seckillId = 1000;
        seckillService.initSeckill(seckillId); //初始化数据
        for (int i = 0; i < 100; i++) {
            int userId = i;
            Runnable seckillTack = new Runnable() {
                @Override
                public void run() {
                    //每个用户秒杀十次
                    for (int j = 0; j < 10; j++) {
                        int finalJ = j;
                        Runnable task = new Runnable() {
                            @Override
                            public void run() {
                                SeckillResult seckillResult = null;
                                try {
                                    seckillResult = seckillService.seckill(userId, seckillId);
                                    if (seckillResult.getCode() == 0) {
                                        logger.info("=======用户" + userId + "====第" + finalJ + "次====秒杀成功：" + seckillResult);
                                    } else {
                                        logger.info("=======用户" + userId + "====第" + finalJ + "次===秒杀失败：" + seckillResult);
                                    }
                                } catch (SeckillException e) {
                                    logger.info("=======用户" + userId + "====第" + finalJ + "次===秒杀失败：" + e.getCodeMsg());
                                }
                                latch.countDown();
                            }
                        };
                        executor.execute(task);
                    }
                }
            };
            executor.execute(seckillTack);
        }
        Thread.sleep(3000);
        System.out.println(latch.getCount());
        return SeckillResult.success();
    }

    /**
     * 方案1：
     */
    @RequestMapping("/betterSeckill")
    public SeckillResult betterSeckill() throws InterruptedException {
        long seckillId = 1000;
        seckillService.initSeckill(seckillId); //初始化数据
        for (int i = 0; i < 100; i++) {
            int userId = i;
            Runnable seckillTack = new Runnable() {
                @Override
                public void run() {
                    //每个用户秒杀十次
                    for (int j = 0; j < 10; j++) {
                        int finalJ = j;
                        Runnable task = new Runnable() {
                            @Override
                            public void run() {
                                SeckillResult seckillResult = null;
                                try {
                                    seckillResult = seckillService.seckill2(userId, seckillId);
                                    if (seckillResult.getCode() == 0) {
                                        logger.info("=======用户" + userId + "====第" + finalJ + "次====秒杀成功：" + seckillResult);
                                    } else {
                                        logger.info("=======用户" + userId + "====第" + finalJ + "次===秒杀失败：" + seckillResult);
                                    }
                                } catch (SeckillException e) {
                                    logger.info("=======用户" + userId + "====第" + finalJ + "次===秒杀失败：" + e.getCodeMsg().getMsg());
                                }
                                latch.countDown();
                            }
                        };
                        executor.execute(task);
                    }
                }
            };
            executor.execute(seckillTack);
        }
        Thread.sleep(3000);
        System.out.println(latch.getCount());
        return SeckillResult.success();
    }
}

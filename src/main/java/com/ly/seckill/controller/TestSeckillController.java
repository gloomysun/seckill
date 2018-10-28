package com.ly.seckill.controller;

import com.ly.seckill.exception.SeckillException;
import com.ly.seckill.result.SeckillResult;
import com.ly.seckill.service.GoodsService;
import com.ly.seckill.service.SeckillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Api(value = "TestSeckillController", description = "秒杀接口测试")
@RestController
@RequestMapping("/testSeckill")
public class TestSeckillController {

    @Autowired
    private SeckillService seckillService;
    @Autowired
    private GoodsService goodsService;

    //创建线程池
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    private static CountDownLatch latch = new CountDownLatch(100);
    private static Logger logger = LoggerFactory.getLogger(TestSeckillController.class);


    /**
     * 初始化数据库数据
     */
    @RequestMapping("/init")
    public void  init(long seckillId){
        seckillService.initSeckill(seckillId);
    }
    /**
     * 方案1：
     */
    @ApiOperation(value = "方案1", notes = "先update后insert")
    @ApiImplicitParam(name = "seckillId", value = "秒杀商品id", required = true, dataType = "long")
    @PostMapping("/simpleSeckill")
    public SeckillResult seckill1(long seckillId) throws InterruptedException {
        seckillService.initSeckill(seckillId); //初始化数据
        for (int i = 0; i < 100; i++) {
            int userId = i;
            Runnable seckillTack = () -> {
                try {
                    SeckillResult seckillResult = seckillService.seckill(userId, seckillId);
                    logger.info("=======用户" + userId + "========秒杀成功：" + seckillResult);
                } catch (SeckillException e) {
                    logger.info("=======用户" + userId + "=======秒杀失败：" + e.getCodeMsg().getMsg());
                }
                latch.countDown();
            };
            executor.execute(seckillTack);
        }
        latch.await();
        System.out.println(latch.getCount());
        return SeckillResult.success();
    }

    /**
     * 方案2：
     */
    @ApiOperation(value = "方案2", notes = "先插入订单后update")
    @ApiImplicitParam(name = "seckillId", value = "秒杀商品id", required = true, dataType = "long")
    @PostMapping("/betterSeckill")
    public SeckillResult seckill2() throws InterruptedException {
        long seckillId = 1000;
        seckillService.initSeckill(seckillId); //初始化数据
        for (int i = 0; i < 100; i++) {
            int userId = i;
            Runnable seckillTack = () -> {
                try {
                    SeckillResult seckillResult = seckillService.seckill2(userId, seckillId);
                    logger.info("=======用户" + userId + "========秒杀成功：" + seckillResult);
                } catch (SeckillException e) {
                    logger.info("=======用户" + userId + "=======秒杀失败：" + e.getCodeMsg().getMsg());
                }
                latch.countDown();
            };
            executor.execute(seckillTack);
        }
        Thread.sleep(3000);
        System.out.println(latch.getCount());
        return SeckillResult.success();
    }
}

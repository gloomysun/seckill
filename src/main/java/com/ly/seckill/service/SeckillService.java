package com.ly.seckill.service;

import com.alibaba.fastjson.JSON;
import com.ly.seckill.common.Constant;
import com.ly.seckill.domain.OrderInfo;
import com.ly.seckill.domain.SeckillGoods;
import com.ly.seckill.domain.SeckillOrder;
import com.ly.seckill.exception.GlobalException;
import com.ly.seckill.exception.SeckillException;
import com.ly.seckill.queue.rabbitmq.RabbitSender;
import com.ly.seckill.queue.rabbitmq.SeckillMsg;
import com.ly.seckill.redis.RedisService;
import com.ly.seckill.result.CodeMsg;
import com.ly.seckill.result.SeckillResult;
import com.ly.seckill.vo.SeckillGoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeckillService implements InitializingBean {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillOrderService seckillOrderService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RabbitSender rabbitSender;

    private Map<Long, Boolean> localOverMap = new HashMap();

    /**
     * 初始化数据库数据
     */
    public void initSeckill(long seckillId) {
        redisService.set(Constant.STOCK_PREFIX, "1000", 100, -1);
        goodsService.initSeckillGoods(seckillId);
        orderService.deleteOrder();
        seckillOrderService.deleteSeckillOrder();
        localOverMap.put(seckillId, false);
    }

    /**
     * 加载库存到redis
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<SeckillGoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (goodsVoList == null)
            return;
        for (SeckillGoodsVo goodsVo : goodsVoList) {
            redisService.set(Constant.STOCK_PREFIX, Long.toString(goodsVo.getSeckillId()), goodsVo.getStockCount(), -1);
            localOverMap.put(goodsVo.getSeckillId(), false);
        }
    }

    /**
     * 秒杀逻辑1:
     * 1、减库存
     * 2、写入秒杀订单
     * 3、下订单
     * Jmeter windows 下测试并发
     */
    @Transactional
    public SeckillResult seckill(long userId, long seckillId) {
        //第三步：减库存
        int count = goodsService.reduceStock(seckillId);
        if (count > 0) {
            //第四步：必须减库存成功后，写入秒杀订单
            //注意插入语句要写insert into 加ignore  忽略插入时抛的重复主键异常
            SeckillOrder successOrder = seckillOrderService.createSeckillOrder(userId, seckillId);
            if (successOrder != null) {
                //写入秒杀订单成功 然后写入订单
                SeckillGoodsVo seckillGoodsVo = goodsService.getGoodsVoBySeckillId(seckillId);
                OrderInfo order = orderService.createOrder(userId, seckillGoodsVo);
                return SeckillResult.success(order);
            } else {
                //插入秒杀订单失败，说明一个用户的两次秒杀请求都走到这导致主键重复
                //抛异常回滚
                return SeckillResult.error(CodeMsg.SECKILL_OVER);
            }
        } else {
            //判断库存的时候有，线程走到减库存时没了
            return SeckillResult.error(CodeMsg.SECKILL_OVER);
        }


    }

    /**
     * 秒杀逻辑2
     * 逻辑层面优化: 先插入秒杀订单，再减库存
     * 1、插入秒杀订单
     * 2、插入成功后再减库存，减库存失败后回滚
     * 3、创建订单
     * <p>
     * 经测试并发比方案1低？
     */
    @Transactional
    public SeckillResult seckill2(long userId, long seckillId) {
        //第一步：创建秒杀订单
        SeckillOrder seckillOrder = seckillOrderService.createSeckillOrder(userId, seckillId);
        if (seckillOrder != null) {
            //写入秒杀订单成功后
            //2、减库存
            int count = goodsService.reduceStock(seckillId);
            if (count > 0) {
                //减库存成功
                //3.写入订单
                SeckillGoodsVo seckillGoodsVo = goodsService.getGoodsVoBySeckillId(seckillId);
                OrderInfo orderInfo = orderService.createOrder(userId, seckillGoodsVo);
                return SeckillResult.success(orderInfo);
            } else {
                return SeckillResult.error(CodeMsg.SECKILL_OVER);
                //减库存失败
//                throw new SeckillException(CodeMsg.SECKILL_OVER);
            }
        } else {
            return SeckillResult.error(CodeMsg.SECKILL_OVER);

            //创建秒杀订单失败
//            throw new SeckillException(CodeMsg.SECKILL_REPEAT);
        }
    }

    //乐观锁
    @Transactional
    public SeckillResult seckill_Three(long userId, long seckillId) {
        try {
            SeckillGoods seckillGoods = goodsService.getSeckillGoodsBySeckillId(seckillId);
            if (seckillGoods.getStockCount() > 0) {
                int count = goodsService.reduceStockByVersion(seckillGoods);
                if (count > 0) {
                    SeckillOrder successOrder = seckillOrderService.createSeckillOrder(userId, seckillId);
                    if (successOrder != null) {
                        //写入秒杀订单成功 然后写入订单
                        SeckillGoodsVo seckillGoodsVo = goodsService.getGoodsVoBySeckillId(seckillId);
                        OrderInfo order = orderService.createOrder(userId, seckillGoodsVo);
                        return SeckillResult.success(order);
                    } else {
                        return SeckillResult.error(CodeMsg.SECKILL_OVER);
                    }
                } else {
                    return SeckillResult.error(CodeMsg.SECKILL_OVER);
                }
            } else {
                return SeckillResult.error(CodeMsg.SECKILL_OVER);
            }
        } catch (SeckillException e) {
            throw e;
        }
    }

    /**
     * 将库存写入redis
     *
     * @param userId
     * @param seckillId
     * @return
     */
    @Transactional
    public SeckillResult seckillRedis(long userId, long seckillId) {
        //内存标记，减少redis访问
        if (localOverMap.get(seckillId)) {
            return SeckillResult.error(CodeMsg.SECKILL_OVER);
        }
        long stock = redisService.decr(Constant.STOCK_PREFIX, Long.toString(seckillId));
        if (stock < 0) {
            localOverMap.put(seckillId, true);//秒杀完毕记为ftrue；
            return SeckillResult.error(CodeMsg.SECKILL_OVER);
        }
        //下面同方案1
        return this.seckill(userId, seckillId);
    }

    /**
     * 异步秒杀
     *
     * @param userId
     * @param seckillId
     * @return
     */
    @Transactional
    public SeckillResult seckillMq(long userId, long seckillId) {
        //内存标记，减少redis访问
        if (localOverMap.get(seckillId)) {
            return SeckillResult.error(CodeMsg.SECKILL_OVER);
        }
        long stock = redisService.decr(Constant.STOCK_PREFIX, Long.toString(seckillId));
        if (stock < 0) {
            localOverMap.put(seckillId, true);//秒杀完毕记为ftrue；
            return SeckillResult.error(CodeMsg.SECKILL_OVER);
        }
        //判断是否已经秒杀到
        SeckillOrder seckillOrder = seckillOrderService.getSeckillOrderByUserIdSeckillId(userId, seckillId);
        if (seckillOrder != null) {
            return SeckillResult.error(CodeMsg.SECKILL_REPEAT);
        }
        //入队
        SeckillMsg seckillMsg = new SeckillMsg();
        seckillMsg.setUserId(userId);
        seckillMsg.setSeckillId(seckillId);
        rabbitSender.sendSeckillMsg(JSON.toJSONString(seckillMsg));

        //给前端一个排队中的loading图，然后通过ajax轮询其他接口返回给用户结果
        return SeckillResult.success(0);
    }
}

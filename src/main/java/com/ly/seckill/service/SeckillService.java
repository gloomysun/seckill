package com.ly.seckill.service;

import com.ly.seckill.domain.OrderInfo;
import com.ly.seckill.domain.SeckillOrder;
import com.ly.seckill.exception.SeckillException;
import com.ly.seckill.result.CodeMsg;
import com.ly.seckill.result.SeckillResult;
import com.ly.seckill.vo.SeckillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeckillService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillOrderService seckillOrderService;

    /**
     * 初始化数据库数据
     */
    public void initSeckill(long seckillId) {
        goodsService.initSeckillGoods(seckillId);
        orderService.deleteOrder();
        seckillOrderService.deleteSeckillOrder();
    }

    /**
     * 秒杀逻辑1:
     * 1、根据seckillId判断秒杀商品库存
     * 2、根据userId和seckillId判断seckill_order表中是否已经有秒杀记录
     * 3、进行减库存
     * 4、写入秒杀订单
     * 5、下订单
     * 弊端：逻辑过于冗余
     */
    @Transactional
    public SeckillResult seckill(long userId, long seckillId) throws SeckillException {
        //第一步：判断库存
        SeckillGoodsVo seckillGoodsVo = goodsService.getGoodsVoBySeckillId(seckillId);
        if (seckillGoodsVo.getStockCount() <= 0) {
            //库存小于0
            throw new SeckillException(CodeMsg.SECKILL_OVER);
        }
        //第二步：判断用户是否已经秒杀
        SeckillOrder seckillOrder = seckillOrderService.getSeckillOrderByUserIdSeckillId(userId, seckillId);
        if (seckillOrder != null) {
            throw new SeckillException(CodeMsg.SECKILL_REPEAT);
        }
        //第三步：减库存
        int count = goodsService.reduceStock(seckillId);
        if (count > 0) {
            //第四步：必须减库存成功后，写入秒杀订单
            //注意插入语句要写insert into 加ignore  忽略插入时抛的重复主键异常
            SeckillOrder successOrder = seckillOrderService.createSeckillOrder(userId, seckillId);
            if (successOrder != null) {
                //写入秒杀订单成功
                OrderInfo order = orderService.createOrder(userId, seckillGoodsVo);
                return SeckillResult.success(order);
            } else {
                //插入秒杀订单失败，说明一个用户的两次秒杀请求都走到这导致主键重复
                //抛异常回滚
                throw new SeckillException(CodeMsg.SECKILL_REPEAT);
            }
        } else {
            //判断库存的时候有，线程走到减库存时没了
            throw new SeckillException(CodeMsg.SECKILL_OVER);
        }
    }

    /**
     * 秒杀逻辑2
     * 逻辑层面优化: 先插入秒杀订单，再减库存
     * 1、插入秒杀订单
     * 2、插入成功后再减库存，减库存失败后回滚
     * 3、创建订单
     */
    @Transactional
    public SeckillResult seckill2(long userId, long seckillId) throws SeckillException {
        //第一步：创建秒杀订单
        SeckillOrder seckillOrder = seckillOrderService.createSeckillOrder(userId, seckillId);
        if (seckillOrder != null) {
            //写入秒杀订单成功后
            //2、减库存
            int count = goodsService.reduceStock(seckillId);
            if (count > 0) {
                //减库存成功
                //3.写入订单
                return SeckillResult.success(seckillOrder);
//                SeckillGoodsVo seckillGoodsVo = goodsService.getGoodsVoBySeckillId(seckillId);
//                OrderInfo orderInfo = orderService.createOrder(userId, seckillGoodsVo);
//                return SeckillResult.success(orderInfo);
            } else {
                //减库存失败
                return SeckillResult.error(CodeMsg.SECKILL_OVER);
            }
        } else {
            //创建秒杀订单失败
            throw new SeckillException(CodeMsg.SECKILL_REPEAT);
        }

    }
}

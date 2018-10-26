package com.ly.seckill.service;

import com.ly.seckill.domain.OrderInfo;
import com.ly.seckill.domain.SeckillOrder;
import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.mapper.OrderMapper;
import com.ly.seckill.utils.SnowFlake;
import com.ly.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SnowFlake snowFlake;

    public SeckillOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderMapper.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }

    public OrderInfo createOrder(SeckillUser user, GoodsVo goodsVo) {
        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(snowFlake.nextId());
        orderInfo.setGoodsCount(1);
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getSeckillPrice());
        orderInfo.setStatus(0);
        orderInfo.setOrderChannel(1);
        orderInfo.setUserId(user.getId());
        orderMapper.insertOrder(user, goodsVo);
        return null;
    }
}

package com.ly.seckill.service;

import com.ly.seckill.domain.OrderInfo;
import com.ly.seckill.domain.SeckillOrder;
import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.mapper.OrderMapper;
import com.ly.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public SeckillOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return orderMapper.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
    }

    public OrderInfo createOrder(SeckillUser user, GoodsVo goodsVo) {
        return null;
    }
}

package com.ly.seckill.service;

import com.ly.seckill.domain.OrderInfo;
import com.ly.seckill.domain.SeckillOrder;
import com.ly.seckill.mapper.OrderMapper;
import com.ly.seckill.utils.SnowFlake;
import com.ly.seckill.vo.SeckillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SnowFlake snowFlake;




    /**
     * 思考：先创建订单还是先创建秒杀订单
     * 秒杀订单可去重
     */
    @Transactional
    public OrderInfo createOrder(long userId, SeckillGoodsVo seckillGoodsVo) {
        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(snowFlake.nextId());
        orderInfo.setGoodsCount(1);
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsId(seckillGoodsVo.getId());
        orderInfo.setGoodsName(seckillGoodsVo.getGoodsName());
        orderInfo.setGoodsPrice(seckillGoodsVo.getSeckillPrice());
        orderInfo.setStatus(0);
        orderInfo.setOrderChannel(1);
        orderInfo.setUserId(userId);
        orderMapper.insertOrder(orderInfo);
        return orderInfo;
    }

    public void deleteOrder() {
        orderMapper.deleteOrder();
    }


}

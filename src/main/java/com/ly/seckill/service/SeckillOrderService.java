package com.ly.seckill.service;

import com.ly.seckill.domain.SeckillOrder;
import com.ly.seckill.exception.SeckillException;
import com.ly.seckill.mapper.SeckillOrderMapper;
import com.ly.seckill.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeckillOrderService {

    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    /**
     * 查询秒杀记录
     * @param userId
     * @param seckillId
     * @return
     */
    public SeckillOrder getSeckillOrderByUserIdSeckillId(Long userId, long seckillId) {
        return seckillOrderMapper.getSeckillOrderByUserIdSeckillId(userId, seckillId);
    }

    /**
     * 计入秒杀订单记录
     */
    @Transactional
    public SeckillOrder createSeckillOrder(long userId, Long seckillId) {
        long orderId = snowFlake.nextId();
        //创建秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setSeckillId(seckillId);
        seckillOrder.setUserId(userId);
        seckillOrder.setOrderId(orderId);
        long count = seckillOrderMapper.insertSeckillOrder(seckillOrder);
        if (count > 0) {
            //插入成功，返回秒杀记录
            return seckillOrder;
        }
        return null;
    }


    public void deleteSeckillOrder() {
        seckillOrderMapper.deleteSeckillOrder();
    }
}
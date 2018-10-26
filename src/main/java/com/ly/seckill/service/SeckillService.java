package com.ly.seckill.service;

import com.ly.seckill.domain.OrderInfo;
import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    /**
     * 秒杀逻辑
     * @param user
     * @param goodsVo
     * @return
     */
    public OrderInfo seckill(SeckillUser user, GoodsVo goodsVo) {
        //减库存
        goodsService.reduceStock(goodsVo);
        //order_info maiosha_order
        return orderService.createOrder(user, goodsVo);
    }
}

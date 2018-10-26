package com.ly.seckill.controller;

import com.ly.seckill.domain.OrderInfo;
import com.ly.seckill.domain.SeckillOrder;
import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.result.CodeMsg;
import com.ly.seckill.result.SeckillResult;
import com.ly.seckill.service.GoodsService;
import com.ly.seckill.service.OrderService;
import com.ly.seckill.service.SeckillService;
import com.ly.seckill.vo.SeckillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀controller
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/do_seckill", method = RequestMethod.POST)
    public SeckillResult doSeckill(SeckillUser user, @RequestParam("seckillId") long seckillId) {
        if (user == null) {
            return SeckillResult.error(CodeMsg.SESSION_ERROR);
        }
        return seckillService.seckill(user.getId(), seckillId);
//        //判断库存
//        SeckillGoodsVo seckillGoodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
//        if (seckillGoodsVo.getStockCount() <= 0) {
//            return SeckillResult.error(CodeMsg.SECKILL_OVER);
//        }
//        //判断用户是否已经秒杀
//        SeckillOrder seckillOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
//        if (seckillOrder != null) {
//            return SeckillResult.error(CodeMsg.SECKILL_REPEAT);
//        }
//        //减库存，下订单，写入秒杀订单
//        OrderInfo orderInfo = seckillService.seckill(user, seckillGoodsVo);
//        return null;
    }
}

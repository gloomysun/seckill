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
import io.swagger.annotations.Api;
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
    }

    @RequestMapping(value = "/do_seckill2", method = RequestMethod.POST)
    public SeckillResult doSeckill2(SeckillUser user, @RequestParam("seckillId") long seckillId) {
        if (user == null) {
            return SeckillResult.error(CodeMsg.SESSION_ERROR);
        }
        return seckillService.seckill2(user.getId(), seckillId);
    }
    @RequestMapping(value = "/do_seckill3", method = RequestMethod.POST)
    public SeckillResult doSeckill3(SeckillUser user, @RequestParam("seckillId") long seckillId) {
        if (user == null) {
            return SeckillResult.error(CodeMsg.SESSION_ERROR);
        }
        return seckillService.seckill_Three(user.getId(), seckillId);
    }
    @RequestMapping(value = "/do_seckill_redis", method = RequestMethod.POST)
    public SeckillResult doSeckillRedis(SeckillUser user, @RequestParam("seckillId") long seckillId) {
        if (user == null) {
            return SeckillResult.error(CodeMsg.SESSION_ERROR);
        }
        return seckillService.seckillRedis(user.getId(), seckillId);
    }
    @RequestMapping(value = "/do_seckill_mq", method = RequestMethod.POST)
    public SeckillResult doSeckillRabbitmq(SeckillUser user, @RequestParam("seckillId") long seckillId) {
        if (user == null) {
            return SeckillResult.error(CodeMsg.SESSION_ERROR);
        }
        return seckillService.seckillMq(user.getId(), seckillId);
    }
}

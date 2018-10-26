package com.ly.seckill.domain;

import lombok.Data;

/**
 * 秒杀订单
 */
@Data
public class SeckillOrder {
    private Long seckillId;
    private Long userId;
    private Long orderId;
}

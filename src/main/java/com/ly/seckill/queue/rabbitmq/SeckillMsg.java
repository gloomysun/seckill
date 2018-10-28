package com.ly.seckill.queue.rabbitmq;

import lombok.Data;

@Data
public class SeckillMsg {
    private long userId;
    private long seckillId;
}

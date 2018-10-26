package com.ly.seckill.vo;

import com.ly.seckill.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * 商品详情页封装bean
 */
@Data
public class SeckillGoodsVo extends Goods {
    private Long seckillId;
    private Double seckillPrice;
    private Integer stockCount;
    private Date startTime;
    private Date endTime;
}

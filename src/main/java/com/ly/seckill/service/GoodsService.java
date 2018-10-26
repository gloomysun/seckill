package com.ly.seckill.service;

import com.ly.seckill.mapper.GoodsMapper;
import com.ly.seckill.vo.SeckillGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    public List<SeckillGoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    public SeckillGoodsVo getGoodsVoBySeckillId(long seckillId) {
        return goodsMapper.getGoodsVoBySeckillId(seckillId);
    }

    public int reduceStock(long seckillId) {
        return goodsMapper.reduceStock(seckillId);
    }


    public void initSeckillGoods(long seckillId) {
        goodsMapper.initSeckillGoods(seckillId);
    }
}

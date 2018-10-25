package com.ly.seckill.service;

import com.ly.seckill.mapper.GoodsMapper;
import com.ly.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    public int getStockCountByGoodsId(long goodsId) {
        return goodsMapper.getStockCountByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVo goodsVo) {
        goodsMapper.reduceStock(goodsVo);
    }
}

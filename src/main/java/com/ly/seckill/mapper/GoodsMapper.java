package com.ly.seckill.mapper;

import com.ly.seckill.vo.SeckillGoodsVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper {
    @Select("select g.id,g.goods_name,g.goods_img,g.goods_price,sg.id as seckillId,sg.seckill_price,sg.stock_count from goods g left join seckill_goods sg on g.id = sg.goods_id")
    List<SeckillGoodsVo> listGoodsVo();

    @Select("select g.id,g.goods_name,g.goods_img,g.goods_price,sg.id as seckill_id,sg.seckill_price,sg.stock_count,sg.start_time,sg.end_time" +
            " from goods g left join seckill_goods sg on g.id = sg.goods_id where sg.id=#{seckillId}")
    SeckillGoodsVo getGoodsVoBySeckillId(long seckillId);

    @Update("update seckill_goods set stock_count = stock_count-1 where id = #{seckillId} and stock_count>0")
    int reduceStock(long seckillId);

    @Update("update seckill_goods set stock_count=10 where id = #{seckillId}")
    void initSeckillGoods(long seckillId);
}

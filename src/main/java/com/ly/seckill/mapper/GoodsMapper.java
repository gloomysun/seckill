package com.ly.seckill.mapper;

import com.ly.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper {
    @Select("select g.id,g.goods_name,g.goods_img,g.goods_price,sg.seckill_price,sg.stock_count from goods g left join seckill_goods sg on g.id = sg.goods_id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.id,g.goods_name,g.goods_img,g.goods_price,sg.seckill_price,sg.stock_count,sg.start_time,sg.end_time" +
            " from goods g left join seckill_goods sg on g.id = sg.goods_id where g.id=#{goodsId}")
    GoodsVo getGoodsVoByGoodsId(long goodsId);

    @Select("select stock_count from seckill_goods where goods_id=#{goodsId")
    int getStockCountByGoodsId(long goodsId);

    @Update("update seckill_goods set stock_count = stock_count-1 where goods_id = #{id} and stock_count>0")
    void reduceStock(GoodsVo goodsVo);
}

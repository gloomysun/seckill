package com.ly.seckill.mapper;

import com.ly.seckill.domain.SeckillOrder;
import com.ly.seckill.domain.SeckillUser;
import com.ly.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {
    @Select("select id,user_id,goods_id,order_id from seckill_order where user_id=#{userId} and goods_id=#{goodsId}")
    SeckillOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    @Insert("insert")
    void insertOrder(SeckillUser user, GoodsVo goodsVo);
}

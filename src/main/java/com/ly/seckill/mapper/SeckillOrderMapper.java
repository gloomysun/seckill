package com.ly.seckill.mapper;

import com.ly.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SeckillOrderMapper {

    /**
     * 根据userid和seckillid查询秒杀订单
     * @param userId
     * @param seckillId
     * @return
     */
    @Select("select user_id,seckill_id,order_id from seckill_order where user_id=#{userId} and seckill_id=#{seckillId}")
    SeckillOrder getSeckillOrderByUserIdSeckillId(@Param("userId") Long userId, @Param("seckillId") long seckillId);
    /**
     * 插入秒杀订单
     * @param seckillOrder
     */
    @Insert("insert ignore into seckill_order (user_id,seckill_id,order_id) values(#{userId},#{seckillId},#{orderId})")
    long insertSeckillOrder(SeckillOrder seckillOrder);



    /**
     * 删除秒杀订单记录
     */
    @Delete("delete from seckill_order")
    void deleteSeckillOrder();
}

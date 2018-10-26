package com.ly.seckill.mapper;

import com.ly.seckill.domain.OrderInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface OrderMapper {
    /**
     * 插入订单
     * @param orderInfo
     */
    @Insert("insert into order_info (id,user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status,create_date)" +
            "values(#{id},#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate} )")
    void insertOrder(OrderInfo orderInfo);

    /**
     * 删除订单记录
     */
    @Delete("delete from order_info")
    void deleteOrder();
}

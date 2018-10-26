package com.ly.seckill.mapper;

import com.ly.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Select;

public interface SeckillUserMapper {

    @Select("select * from seckill_user where id = #{id}")
    SeckillUser getById(String id);
}

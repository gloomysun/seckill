package com.ly.seckill.mapper;

import com.ly.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Select;

public interface SeckillUserMapper {

    @Select("select * from seckillUser where id = #{id}")
    SeckillUser getById(String id);
}

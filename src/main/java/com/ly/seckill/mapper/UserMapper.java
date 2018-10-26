package com.ly.seckill.mapper;

import com.ly.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.cache.annotation.Cacheable;

public interface UserMapper {
    @Cacheable("cache1")
//Cache是发生在cache1上的
    User getUserById(int id);

    @Insert("insert into user(name) values (#{name})")
    @Options(useGeneratedKeys=true, keyProperty="id",keyColumn="id")
//    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    int insert(User user);
}

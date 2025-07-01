package com.example.Kcsj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.Kcsj.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {  //操作Users实体
    //根据用户名查询用户的密码
    @Select("select password from user where username=#{username}")
    User selectByName(String username);

    //根据用户名查询用户的所有信息
    @Select("select * from user where username = #{username}")
    User selectByUsername(String username);
}

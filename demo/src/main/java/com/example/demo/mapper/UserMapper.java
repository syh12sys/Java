package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    @Select("select * from user_info where username=#{username} limit 1")
    UserEntity selectByUserName(@Param("username") String username);

    @Select("select * from user_info where username=#{username} and password=#{password} limit 1")
    UserEntity login(@Param("username") String username, @Param("password") String password);

    @Insert("insert into user_info(username,password,status) values(#{userDTO.username},#{userDTO.password},1)")
    @SelectKey(statement = "select id from user_info where username=#{userDTO.username}", keyProperty = "userDTO.id", before = false, resultType = int.class)
    int register(@Param("userDTO") UserDTO userDTO);

    @Update("update usercenter.user_info set password=#{newPassord} where usercenter.user_info.username=#{userName}")
    void modifyPassword(@Param("userName") String userName,  @Param("newPassord") String newPassord);
}

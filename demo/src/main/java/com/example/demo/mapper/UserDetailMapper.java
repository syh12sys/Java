package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.UserDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDetailMapper extends BaseMapper<UserDetailEntity> {
    @Select("select * from user_info_detail where user_id=#{userId}")
    UserDetailEntity selectByUserId(@Param("userId") Integer userId);
}

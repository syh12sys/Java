package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {
    @Select("select order_info.* from order_info join user_info on order_info.user_id = user_info.id where user_info.token=#{userToken}")
    List<OrderEntity> selectByUserToken(@Param("userToken") String userToken);
}

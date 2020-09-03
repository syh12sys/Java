package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.PhoneAddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PhoneAddressMapper extends BaseMapper<PhoneAddressEntity> {
    @Select("select * from address_phone")
    public List<PhoneAddressEntity> selectAll();
}

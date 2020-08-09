package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Cipher;
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

    @Select("select * from user_info where token=#{userToken} limit 1")
    UserEntity selectByToken(@Param("userToken") String userToken);

    @Cipher(value = "decrypt")
    @Select("select * from user_info where username=#{username} and password=#{password} limit 1")
    UserEntity login(@Param("username") String username, @Param("password") String password);

    @Update("update user_info set token=#{userToken}, login_datetime=now() where id=#{id}")
    int updateUserToken(@Param("id") Integer id, @Param("userToken") String userToken);

    @Cipher(value = "encrypt")
    @Insert("insert into user_info(username,password,status) values(#{userEntity.username},#{userEntity.password},1)")
    @SelectKey(statement = "select id from user_info where username=#{userEntity.username}", keyProperty = "userEntity.id", before = false, resultType = int.class)
    int register(@Param("userEntity") UserEntity userEntity);

    @Update("update user_info set password=#{newPassord} where username=#{userName}")
    int modifyPassword(@Param("userName") String userName,  @Param("newPassord") String newPassord);

    @Update("update user_info set test_optimistic_lock_count = test_optimistic_lock_count + 1, update_at=now() where id = #{userEntity.id} and update_at = #{userEntity.updateAt}")
    int updateTestOptimisticLockCount(@Param("userEntity") UserEntity userEntity);
}

package com.example.demo.service;

import com.example.demo.controller.RestRetValue;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    private Boolean isValidUserName(String userName){
        if(userName.length()>40 || userName.length()<6){
            return false;
        }
        return true;
    }

    public RestRetValue<Boolean> register(UserDTO userDTO){
        RestRetValue<Boolean> registerRet = new RestRetValue<Boolean>();
        if(!isValidUserName(userDTO.getUsername())){
            registerRet.setReturnValue("10001", "用户名不合法", false);
        }
        else if(isUserExist(userDTO.getUsername())){
            registerRet.setReturnValue("10002", "用户名已存在", false);
        }
        else{
            int id = userMapper.register(userDTO);
            registerRet.setData(true);
        }

        return registerRet;
    }

    public Boolean login(String userName, String password){
        if(userName == null || userName.isEmpty() || password == null || password.isEmpty()){
            return false;
        }
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("username", userName);
        columnMap.put("password", password);
        UserEntity user = userMapper.login(userName, password);
        //List userList = userMapper.selectByMap(columnMap);
        return user != null;
    }

    public Boolean modifyPassword(String userName, String password, String newPassword) {
        if (userName == null || userName.isEmpty() ||
                password == null || password.isEmpty() ||
                newPassword == null || newPassword.isEmpty()) {
            return false;
        }
        if (!isValidUserName(userName)) {
            return false;
        }

        if (!login(userName, password)) {
            return false;
        }

        userMapper.modifyPassword(userName, newPassword);
        return true;
    }

    public Boolean isUserExist(String userName){
        return userMapper.selectByUserName(userName) != null;
    }



}

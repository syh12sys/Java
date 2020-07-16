package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//表示该controller类下所有的方法都公用的一级上下文根
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userSerivce;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String register(@RequestBody UserDTO userDTO){
        return JSON.toJSONString(userSerivce.register(userDTO));
    }


    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    String getUserByGet(@RequestParam(value = "userName") String userName){
        return "Hello " + userName;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String login(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password){
        Boolean isSuc = userSerivce.login(userName, password);
        RestRetValue tmp = new RestRetValue("0", "", isSuc);
        return JSON.toJSONString(tmp);
    }
}

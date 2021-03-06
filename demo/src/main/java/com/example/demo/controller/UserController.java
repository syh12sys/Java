package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.PhoneAddressEntity;
import com.example.demo.service.PhoneAddressService;
import com.example.demo.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@RestController
//表示该controller类下所有的方法都公用的一级上下文根
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userSerivce;

    @Autowired
    PhoneAddressService phoneAddressService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    String register(UserDTO userDTO) throws IOException {
        return JSON.toJSONString(userSerivce.register(userDTO));
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    String getUserByGet(@RequestParam(value = "userName") String userName){
        return "Hello " + userName;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String login(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password){
        String userToken = userSerivce.login(userName, password);
        RestRetValue tmp = new RestRetValue("0", "", userToken == null ? "" : userToken);
        return JSON.toJSONString(tmp);
    }

    @RequestMapping(value = "/modify_password", method = RequestMethod.GET)
    String modifyPassord(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password, @RequestParam(value = "newPassword") String newPassword) {
            Boolean isSuc = userSerivce.modifyPassword(userName, password, newPassword);
            RestRetValue tmp = new RestRetValue("0", "", isSuc);
            return JSON.toJSONString(tmp);
    }

    @RequestMapping(value = "/queryUserForPage", method = RequestMethod.GET)
    String queryUserForPage(@RequestParam(value = "current") long current, @RequestParam(value = "size") long size) {
        List<UserDTO> users = userSerivce.queryUserForPage(current, size);
        RestRetValue response = new RestRetValue("0", "", users);
        return JSON.toJSONString(response);
    }

    @RequestMapping(value = "/testOptimisticLock", method = RequestMethod.GET)
    String testOptimisticLock() {
        boolean ret = userSerivce.testOptimisticLock();
        RestRetValue response = new RestRetValue("0", "", ret);
        return JSON.toJSONString(response);
    }

    @RequestMapping(value = "/testSubDatabase", method = RequestMethod.GET)
    String testSubDatabase() {
        userSerivce.testSubDatabase();
        RestRetValue response = new RestRetValue("0", "", true);
        return JSON.toJSONString(response);
    }

    @RequestMapping(value = "/testErrorControl", method = RequestMethod.GET)
    String testErrorControl() {
        throw new NullPointerException("TestController have exception");
    }

    @RequestMapping(value = "/getPhoneAddress", method = RequestMethod.GET)
    String getPhoneAddress(@RequestParam(value = "phone") String phone) {
         PhoneAddressEntity phoneAddressEntity = phoneAddressService.getPhoneAddress(phone);
         RestRetValue response = new RestRetValue("0", "", phoneAddressEntity == null ? "" : phoneAddressEntity);
         return  JSON.toJSONString(response);
    }
}

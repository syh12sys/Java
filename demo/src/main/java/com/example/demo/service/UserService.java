package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.config.DynamicDataSourceContextHolder;
import com.example.demo.controller.RestRetValue;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Cipher;
import com.example.demo.entity.UserDetailEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserDetailMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

// java练习记录：springMVC（serverlet）和mybatis（DataSource），注解是关键
// 1. springMVC三层框架：修改密码
// 2. 拦截器：筛选不想要的， 鉴权
// 3. 过滤器：过滤request，筛选想要的
// 4. 分页查询：mybatis提供功能
// 5. 事物：可靠，多个service函数调用，单个mapper函数已经在事务里面；垂直分表
// 6. 异步事件机制：解耦、异步
// 7. 悲观锁、乐观锁：性能差当稳定、性能好不稳定？ 互联网的业务都要考虑锁的问题？
// 8. 读写分离：如何动态的扩展数据服务器？切面不能切entity这种自定义类？ 右键菜单新建的切面类 *ja是什么东西？
@Service
public class UserService implements ApplicationEventPublisherAware {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDetailMapper userDetailMapper;

    // 事件发布者，目的是解耦
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    OptimisticLockService optimisticLockService;

    private Boolean isValidUserName(String userName) {
        if (userName.length() > 40 || userName.length() < 6) {
            return false;
        }
        return true;
    }

    // 注册
    @Transactional
    public RestRetValue<Boolean> register(UserDTO userDTO) {
        RestRetValue<Boolean> registerRet = new RestRetValue<Boolean>();
        if (!isValidUserName(userDTO.getUserName())) {
            registerRet.setReturnValue("10001", "用户名不合法", false);
        } else if (isUserExist(userDTO.getUserName())) {
            registerRet.setReturnValue("10002", "用户名已存在", false);
        } else {
            // 基本信息
            UserEntity userEntity = new UserEntity();
            userEntity.setPassword(userDTO.getPassword());
            userEntity.setUsername(userDTO.getUserName());
            userEntity.setPhoneNumber(userDTO.getPhoneNumber());
            userMapper.register(userEntity);

            // 详细信息
            UserDetailEntity userDetailEntity = new UserDetailEntity();
            userDetailEntity.setAddress(userDTO.getAddress());
            userDetailEntity.setAge(userDTO.getAge());
            userDetailEntity.setInterest(userDTO.getInterest());
            userDetailEntity.setUserId(userEntity.getId());
            userDetailEntity.setSex(userDTO.getSex());
            userDetailMapper.insert(userDetailEntity);

            // 注册成功发送短信通知用户
            applicationEventPublisher.publishEvent(new PhoneMessageEvent(userEntity.getPhoneNumber()));

            registerRet.setData(true);
        }
        return registerRet;
    }

    // 登录
    @DS(value = "slave")
    public String login(String userName, String password) {
        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }

        String userToken = null;
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("username", userName);
        columnMap.put("password", password);
        UserEntity user = userMapper.login(userName, password);
        //List userList = userMapper.selectByMap(columnMap);
        if (user == null) {
            return null;
        }

        try {
            Class<?> userClass = user.getClass();
            Method method = user.getClass().getMethod("setPhoneNumber", String.class);
            Object[] methods = user.getClass().getMethods();
            boolean ret =  method.isAnnotationPresent(Cipher.class);
        } catch (Throwable throwable) {

        }



        // 登录成功更新token
        userToken = generateUserToken(userName);
        if (userToken == null) {
            return null;
        }
        userMapper.updateUserToken(user.getId(), userToken);

        return userToken;
    }

    // 修改密码
    public Boolean modifyPassword(String userName, String password, String newPassword) {
        if (userName == null || userName.isEmpty() ||
                password == null || password.isEmpty() ||
                newPassword == null || newPassword.isEmpty()) {
            return false;
        }
        if (!isValidUserName(userName)) {
            return false;
        }

        if (login(userName, password) == null) {
            return false;
        }

        userMapper.modifyPassword(userName, newPassword);
        return true;
    }

    public Boolean isUserExist(String userName) {
        return userMapper.selectByUserName(userName) != null;
    }

    // 分页查询
    public List<UserDTO> queryUserForPage(long current, long size) {
        IPage<UserEntity> userPage = new Page<>(current, size);
        userPage = userMapper.selectPage(userPage, null);
        List<UserEntity> records = userPage.getRecords();
        List<UserDTO> userDTORecords = new ArrayList<>();
        for (UserEntity user : records) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setUserName(user.getUsername());
            userDTORecords.add(userDTO);
        }
        return userDTORecords;
    }

    // 签权
    public boolean authenticate() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userToken = request.getParameter("userToken");
        // 没有token
        if (userToken == null || userToken.isEmpty()) {
            return false;
        }

        // token是否过期
        UserEntity userEntity = userMapper.selectByToken(userToken);
        if (userEntity == null) {
            return false;
        }
        Date loginDatetime = userEntity.getLoginDatetime();
        if (loginDatetime == null) {
            return false;
        }
        long passTime = System.currentTimeMillis() - loginDatetime.getTime();
        // 时间大于10天，认为token过期，续重新登录
        return passTime <= 10 * 24 * 60 * 60 * 1000;
    }

    public boolean testOptimisticLock() {
        optimisticLockService.increaseOptimisticLockCount();
        return true;
    }

    // 生成用户唯一标识：用户名 + user-agent + postman-token
    private String generateUserToken(String userName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String userAgent = request.getHeader("user-agent");
        String userPostmanToken = request.getHeader("postman-token");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((userName + userAgent + userPostmanToken).getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}

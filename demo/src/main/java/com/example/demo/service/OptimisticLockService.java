package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 乐观锁测试类
@Service
public class OptimisticLockService {
    @Autowired
    UserMapper userMapper;

    public void increaseOptimisticLockCount(Integer id) {
        int tryTimes = 0;
        while (true) {
            ++tryTimes;
            if (internalIncreaseOptimisticLockCount(id) != 0) {
                break;
            }
            if (tryTimes == 200) {
                
            }

        }
    }

    // 增加乐观锁计数
    @Transactional
    public Integer internalIncreaseOptimisticLockCount(Integer id) {
        UserEntity userEntity = userMapper.selectById(id);
        if (userEntity == null) {
            return 0;
        }
        return userMapper.updateTestOptimisticLockCount(userEntity);
    }
}

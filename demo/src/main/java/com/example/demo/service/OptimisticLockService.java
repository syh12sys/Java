package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.TimeUnit;

// 乐观锁测试类
@Service
public class OptimisticLockService {
    @Autowired
    UserMapper userMapper;

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    public void increaseOptimisticLockCount() {
        int threaCount = 100;
        while (threaCount-- > 0) {
            // TODO: 线程池还不会用，暂时先创建线程
            new Thread(()->increaseOptimisticLockCount(1)).start();
        }
    }

    // 如果更新出错，则尝试200次
    public void increaseOptimisticLockCount(Integer id) {
        int tryTimes = 0;
        while (true) {
            ++tryTimes;
            if (internalIncreaseOptimisticLockCount(id) != 0) {
                break;
            }
            if (tryTimes == 200) {
                log.warn("重试更新超过200此，放弃本次更新");
                break;
            }

            try {
                TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        log.info("tryTimes:{}", tryTimes);
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

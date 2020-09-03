package com.example.demo;

import com.example.demo.redis.LockCase1;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Order(value = 5)
public class RedisLockTester implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10, 1, TimeUnit.SECONDS, new SynchronousQueue<>());
        for (int i = 0; i < pool.getMaximumPoolSize(); ++i) {
            pool.submit(() -> {
                try {
                    Jedis jedis = new Jedis("localhost");
                    LockCase1 lock = new LockCase1(jedis, "孙迎世");
                    lock.lock();
                    Thread.sleep(1000);
                    lock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        while (pool.getPoolSize() != 0) {
        }
    }
}

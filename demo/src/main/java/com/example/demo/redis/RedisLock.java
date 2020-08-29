package com.example.demo.redis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.locks.Lock;

public abstract class RedisLock implements Lock {
    protected Jedis jedis;

    protected String lockKey;

    public RedisLock(Jedis jedis, String lockKey) {
        this.jedis = jedis;
        this.lockKey = lockKey;
    }

    public void sleepBySencod(int sencond) {
        try {
            Thread.sleep(sencond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.example.demo.redis;

import redis.clients.jedis.Jedis;
import sun.rmi.runtime.Log;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public abstract class RedisLock implements Lock {
    protected Jedis jedis;

    protected String lockKey;

    protected String lockValue;

    protected volatile AtomicBoolean isOpenExpirationRenewal = new AtomicBoolean(true);

    private Thread renewalThread = new Thread(new ExirationRenewal());

    public RedisLock(Jedis jedis, String lockKey) {
        this(jedis, lockKey, UUID.randomUUID().toString()+Thread.currentThread().getId());
    }

    public RedisLock(Jedis jedis, String lockKey, String lockValue) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.lockValue = lockValue;
    }

    public void sleepBySencod(int sencond) {
        try {
            Thread.sleep(sencond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lockInterruptibly() {}

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        return false;
    }

    protected void scheduleExpirationRenewal() {
        if (renewalThread.isAlive()) {
            return;
        }

        isOpenExpirationRenewal.set(true);
        renewalThread.start();
    }

    private class ExirationRenewal implements Runnable {
        @Override
        public void run() {
            while (isOpenExpirationRenewal.get()) {
                System.out.println("执行延迟失效时间中...");

                String checkAndExpireScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "return redis.call('expire',KEYS[1], ARGV[2]) " +
                        "else " +
                        "return 0 end";
                jedis.eval(checkAndExpireScript, 1, lockKey, lockValue, "30");

                sleepBySencod(1);
            }
        }
    }
}

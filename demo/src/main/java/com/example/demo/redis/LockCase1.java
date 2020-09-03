package com.example.demo.redis;

import redis.clients.jedis.Jedis;

// 几个重要问题
// 问题一： 可靠性，如果客户端挂掉，那么锁将无人释放，其它客户端永远得不到锁
// 解决方案：锁设置超时
// 场景：
// 客户端A获取到锁，过期时间30S，
// 客户端A在某个操作上阻塞50S
// redis时间到30S，自动释放锁
// 客户端B获取到同一个锁
// 客户端A从阻塞中恢复，释放掉B持有的锁
// 问题二：如果保证过期时间大于业务执行时间?
// 解决方案：使用线程定时刷新锁
// 问题三：如何保证锁不会被误删？
// 解决方案：使用UUID标识标识客户端
// 问题四：非原子性释放锁可能导致误释放问题
// 解决方案：Lua脚本或事物

public class LockCase1 extends RedisLock {
    public LockCase1(Jedis jedis, String name) {
        super(jedis, name);
    }

    @Override
    public void lock() {
        while (true) {
            String result = jedis.set(lockKey, lockValue, LockConstants.NOT_EXIST, LockConstants.SECONDS, 30);
            if (LockConstants.OK.equals(result)) {
                System.out.println(Thread.currentThread().getId() + " 加锁成功");

                scheduleExpirationRenewal();
                break;
            }
            System.out.println(Thread.currentThread().getId() + "加锁失败，等待1S后继续获取");
            sleepBySencod(1);
        }
    }

    @Override
    public void unlock() {
//        String lockValue = jedis.get(lockKey);
//        if (lockValue.equals(this.lockKey)) {
//        问题：还是类前面描述的场景，客户A可能释放了客户端B的锁
//            jedis.del(lockKey);
//        }
        isOpenExpirationRenewal.set(false);
        System.out.println(Thread.currentThread().getId() + " 释放锁");
        String checkAndDelScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]) " +
                "else " +
                "return 0 " +
                "end";
        jedis.eval(checkAndDelScript, 1, lockKey, lockValue);
    }
}

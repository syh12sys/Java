package com.example.demo.ScheduledTask;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SimpleScheduledTask {
    public SimpleScheduledTask() {
    }

    @Scheduled(fixedRate = 5000)
    private void run() {
        System.out.println("定时任务执行时间：" + LocalDateTime.now());
    }
}

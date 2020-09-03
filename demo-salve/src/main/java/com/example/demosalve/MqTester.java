package com.example.demosalve;

import com.example.demosalve.mq.Consumer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class MqTester implements ApplicationRunner {
    Consumer consumer = new Consumer();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumer.start();
    }
}

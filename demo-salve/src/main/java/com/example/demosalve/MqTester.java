package com.example.demosalve;

import com.example.demosalve.mq.Consumer;
import com.example.demosalve.mq.Consumer2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class MqTester implements ApplicationRunner {
    Consumer consumer = new Consumer();

    Consumer2 consumer2 = new Consumer2();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        consumer.start();

        consumer2.start();
    }
}

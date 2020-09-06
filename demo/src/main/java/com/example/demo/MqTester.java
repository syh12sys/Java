package com.example.demo;

import com.example.demo.mq.JmsConfig;
import com.example.demo.mq.Producer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Order(value = 2)
public class MqTester implements ApplicationRunner {
    Producer producer = new Producer();

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 启动生产者
        producer.start();

        List<String> msgs = new ArrayList<>();
        msgs.add("孙迎世");
//        msgs.add("梁海燕");
//        msgs.add("孙佳阳");
//        msgs.add("13141516");
        for (String s : msgs) {
            Message message = new Message(JmsConfig.TOPIC, "testtag", s.getBytes("utf-8"));
            SendResult sendResult = producer.send(message);
            log.info("生产者：生产消息 {} , result={}", s, sendResult.getSendStatus());
        }
    }
}

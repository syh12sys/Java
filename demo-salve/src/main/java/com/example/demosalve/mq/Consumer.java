package com.example.demosalve.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class Consumer {
    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    private DefaultMQPushConsumer consumer;

    public static final String CONSUMER_GROUP = "test_consumer";

    public Consumer() {
        consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        // 一个新的订阅组从独立额的最后位置开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

    }

    public void start() throws Exception {
        consumer.subscribe("topic_family", "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context)->{
            try {
                for (Message msg : msgs) {
                    String body = new String(msg.getBody(), "utf-8");
                    log.info("Consumer-获取消息:topic={}, 消息={}", msg.getTopic(), body);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}

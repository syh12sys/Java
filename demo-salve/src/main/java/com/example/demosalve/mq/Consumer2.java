package com.example.demosalve.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.LoggerFactory;

public class Consumer2 {
    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    private DefaultMQPushConsumer consumer;

    public static final String CONSUMER_GROUP = "test_consumer";

    public Consumer2() {
        consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        // 程序第一次启动从队列头取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 批量消费，默认是1条
        consumer.setConsumeMessageBatchMaxSize(1);
    }

    public void start() throws Exception {
        consumer.subscribe("topic_family", "*");
        consumer.registerMessageListener((MessageListenerConcurrently)(msgs, context)->{
            MessageExt message = msgs.get(0);
            try {
                String body = new String(message.getBody(), "utf-8");
                log.info("Consumer2获取消息，topic={}, message={}", message.getTopic(), body);
            } catch (Exception e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}

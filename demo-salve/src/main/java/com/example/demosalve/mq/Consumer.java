package com.example.demosalve.mq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class Consumer {
    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    private DefaultMQPushConsumer consumer;

    public static final String CONSUMER_GROUP = "test_consumer";

    public Consumer() {
        consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        // 程序第一次启动从队列头取数据
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 批量消费，默认是1条
        consumer.setConsumeMessageBatchMaxSize(1);
    }

    public void start() throws Exception {
        consumer.subscribe("topic_family", "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context)->{
            MessageExt message = msgs.get(0);
            try {
                String body = new String(message.getBody(), "utf-8");
                log.info("Consumer-获取消息:topic={}, 消息={}", message.getTopic(), body);

                Thread.sleep(60000);
//                int i = 1 / 0;
//                System.out.println(i);
            } catch (Exception e) {
                e.printStackTrace();
                if (message.getReconsumeTimes() == 3) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }
}

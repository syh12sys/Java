package com.example.demo.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.LoggerFactory;

public class Producer {
    private String producerGroup = "test_producer";
    private DefaultMQProducer producer;

    protected final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());

    public Producer() {
        //示例生产者
        producer = new DefaultMQProducer(producerGroup);
        //不开启vip通道 开通口端口会减2
        producer.setVipChannelEnabled(false);
        //绑定name server
        producer.setNamesrvAddr(JmsConfig.SERVER_NAME);
    }

    /**
     * 对象在使用之前必须要调用一次，只能初始化一次
     */
    public void start(){
        try {
            this.producer.start();
            log.info("生产者启动成功");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public DefaultMQProducer getProducer(){
        return this.producer;
    }

    public void shutdown() {
        this.producer.shutdown();
    }
}

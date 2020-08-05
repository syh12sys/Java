package com.example.demo.service;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PhoneMessageService implements ApplicationListener<PhoneMessageEvent> {
    public void sendPhoneMessage(String phoneNumber) {
        System.out.printf("用户 %s 已注册成功\n", phoneNumber);
    }

    @Override
    public void onApplicationEvent(PhoneMessageEvent phoneMessageEvent) {
        sendPhoneMessage((String) phoneMessageEvent.getSource());
    }
}

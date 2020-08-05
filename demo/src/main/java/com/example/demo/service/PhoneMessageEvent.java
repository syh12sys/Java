package com.example.demo.service;

import org.springframework.context.ApplicationEvent;

public class PhoneMessageEvent extends ApplicationEvent {
    // 电话号码
    private String phoneNumber;

    public PhoneMessageEvent(String phoneNumber) {
        super(phoneNumber);
        this.phoneNumber = phoneNumber;
    }
}

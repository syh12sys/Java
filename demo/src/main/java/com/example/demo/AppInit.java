package com.example.demo;


import com.example.demo.redis.PhoneAdressLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements ApplicationRunner {
    @Autowired
    private PhoneAdressLoader phoneAdressLoader;

    @Override
    public void run(ApplicationArguments arguments) throws Exception {
        phoneAdressLoader.loadData();
    }
}

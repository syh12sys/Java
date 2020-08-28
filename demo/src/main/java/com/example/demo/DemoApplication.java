package com.example.demo;

import com.example.demo.redis.PhoneAdressLoader;
import io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, SpringBootConfiguration.class})
//@SpringBootApplication
@ServletComponentScan("com.example.demo.filter")
public class DemoApplication {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
        SpringApplication.run(DemoApplication.class, args);
    }
}


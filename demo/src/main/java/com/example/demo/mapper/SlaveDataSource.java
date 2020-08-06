package com.example.demo.mapper;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

//@Configuration
//public class SlaveDataSource {
//    @Bean(name = "SlaveDataSource")
//    public DataSource dataSource(Environment env) {
//        HikariDataSource hikariDataSource = new HikariDataSource();
//        hikariDataSource.setJdbcUrl(env.getProperty("spring.datasource.slave.url"));
//        hikariDataSource.setUsername(env.getProperty("spring.datasource.slave.username"));
//        hikariDataSource.setPassword(env.getProperty("spring.datasource.slave.password"));
//        hikariDataSource.setDriverClassName(env.getProperty("spring.datasource.slave.driver-class-name"));
//        return hikariDataSource;
//    }
//}

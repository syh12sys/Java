package com.example.demo.mapper;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class MasterDataSource {
    @Bean(name = "MasterDataSource")
    public DataSource dataSource(Environment env) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(env.getProperty("spring.datasource.master.url"));
        hikariDataSource.setUsername(env.getProperty("spring.datasource.master.username"));
        hikariDataSource.setPassword(env.getProperty("spring.datasource.master.password"));
        hikariDataSource.setDriverClassName(env.getProperty("spring.datasource.master.driver-class-name"));
        return  hikariDataSource;
    }
}

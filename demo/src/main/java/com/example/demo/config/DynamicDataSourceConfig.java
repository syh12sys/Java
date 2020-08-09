package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("file:G:/Java/demo/src/main/java/com/example/demo/config/jdbc.properties")
@MapperScan(basePackages = "com.example.demo.mapper")
public class DynamicDataSourceConfig {
    @Bean(value = "master")
    @ConfigurationProperties(prefix = "spring.datasource.mater")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(value = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        Map<Object, Object> dataSources = new HashMap<>(2);
        dataSources.put("master", masterDataSource());
        dataSources.put("slave", slaveDataSource());
        // 设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        return dynamicDataSource;
    }
}

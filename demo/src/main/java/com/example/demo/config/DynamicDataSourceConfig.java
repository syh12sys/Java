package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource(value= {"classpath:application-spring-datasource.properties"})
@MapperScan(basePackages = "com.example.demo.mapper")
// spring-datasource数据源此类才生效
@Profile("spring-datasource")
public class DynamicDataSourceConfig {
    DynamicDataSourceConfig()
    {
    }

    // TODO: Bean是有用的，只有使用了Bean注解，有效的DataSource才会被创建出来（包含配置）
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
        //TODO: 这里不会创建新的对象，直接引用了Bean容器中的对象，为什么？
        dataSources.put("master", masterDataSource());
        dataSources.put("slave", slaveDataSource());
        // 设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        return dynamicDataSource;
    }
}

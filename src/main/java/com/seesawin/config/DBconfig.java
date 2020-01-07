package com.seesawin.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBconfig {
    /**
     * 配置資料來源
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean(name = "dataSource")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }
}

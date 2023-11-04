package com.msa.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("live")
public class LiveDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://845955729714.dkr.ecr.ap-northeast-2.amazonaws.com:5432/yctechaws?useSSL=false&allowPublicKeyRetrieval=true")
                .driverClassName("org.h2.Driver")
                .username("sa")
                .password("password")
                .build();
    }
}
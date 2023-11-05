

/*
package com.msa.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("dev")
public class DevDataSourceConfig {
    @Bean  // 에러남 고치기.
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url("jdbc:postgresql://postgres.cmdzixdnyglk.ap-northeast-2.rds.amazonaws.com:5432/postgres?useSSL=false&allowPublicKeyRetrieval=true")
                .driverClassName("org.postgresql.Driver")
                .username("postgres")
                .password("1234qwer")
                .build();
    }
}
*/
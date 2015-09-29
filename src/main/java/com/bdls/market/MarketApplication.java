package com.bdls.market;

import com.bdls.market.config.DataSourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[] {MarketApplication.class, DataSourceConfig.class}, args);
    }
}

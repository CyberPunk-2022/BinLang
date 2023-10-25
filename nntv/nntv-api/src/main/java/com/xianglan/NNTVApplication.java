package com.xianglan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class NNTVApplication {
    public static void main(String[] args) {
        SpringApplication.run(NNTVApplication.class);
    }
}

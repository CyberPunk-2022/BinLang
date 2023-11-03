package com.xianglan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = {"com.xianglan.qnytv.mapper"})
public class NNTVApplication {
    public static void main(String[] args) {
        SpringApplication.run(NNTVApplication.class);
    }
}

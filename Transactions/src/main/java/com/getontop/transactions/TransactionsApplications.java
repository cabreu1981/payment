package com.getontop.transactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients( basePackages = "com.getontop.clients")
public class TransactionsApplications {

    public static void main(String[] args) {
        SpringApplication.run(TransactionsApplications.class, args);
    }
}

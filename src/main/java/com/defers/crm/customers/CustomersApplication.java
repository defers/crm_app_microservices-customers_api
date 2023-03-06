package com.defers.crm.customers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// TODO scheduler for classification of customers by some parametres
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties
public class CustomersApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}

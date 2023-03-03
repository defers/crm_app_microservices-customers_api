package com.defers.crm.customers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/test1")
@EnableDiscoveryClient
@EnableConfigurationProperties
@RefreshScope
public class CustomersApplication implements CommandLineRunner{
    @Value("${customers.test}")
    private String test;
    @Value("${customers.value}")
    private int value;
    @Value("${app.data.default-number-on-page}")
    private Integer numberOnPage;

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(test);
        System.out.println(value);
        System.out.println(numberOnPage);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<String> getMsg() {
        return new ResponseEntity<>("Hello!", HttpStatus.OK);
    }

}

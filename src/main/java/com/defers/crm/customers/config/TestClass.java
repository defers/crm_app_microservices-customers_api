package com.defers.crm.customers.config;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@RefreshScope
//@ConfigurationProperties(prefix = "customers")
public class TestClass {
    @Value("${customers.value}")
    private int value;

    @Value("${customers.test}")
    private String test;

    public void sendMsg() {
//        log.info("YO YO HHH");
//        System.out.println(value);
//        System.out.println("test " + test);
    }
}

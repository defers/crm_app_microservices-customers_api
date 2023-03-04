package com.defers.crm.customers.repository;

import com.defers.crm.customers.config.DBConfiguration;
import com.defers.crm.customers.entity.Customer;
import com.defers.crm.customers.enums.CustomerType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@Import(DBConfiguration.class)
@DataRedisTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void findByNameAndDeleted() {
        String id1 = "fa994eca-23b4-4cb2-abed-cf7d16fa3735";

        Customer customer = Customer.builder()
                .id(id1)
                .name("partner1")
                .type(CustomerType.CORPORATE)
                .createdDate(LocalDateTime.of(2023, 1, 1, 17, 10))
                .build();

        customerRepository.save(customer);

        Customer customerFound = customerRepository
                .findByNameAndDeleted("partner1", false)
                .get();

        Assertions.assertThat(customerFound)
                .isNotNull().hasFieldOrPropertyWithValue("id", id1);
    }

    @Test
    void findByName() {
        String id1 = "fa994eca-23b4-4cb2-abed-cf7d16fa3735";

        Customer customer = Customer.builder()
                .id(id1)
                .name("partner1")
                .type(CustomerType.CORPORATE)
                .createdDate(LocalDateTime.of(2023, 1, 1, 17, 10))
                .build();

        customerRepository.save(customer);

        Customer customerFound = customerRepository
                .findByName("partner1")
                .get();

        Assertions.assertThat(customerFound)
                .isNotNull().hasFieldOrPropertyWithValue("id", id1);
    }
}
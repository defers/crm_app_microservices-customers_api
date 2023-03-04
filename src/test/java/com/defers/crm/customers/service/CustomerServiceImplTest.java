package com.defers.crm.customers.service;

import com.defers.crm.customers.config.AppConfiguration;
import com.defers.crm.customers.dto.CustomerDTO;
import com.defers.crm.customers.dto.dtomapper.CustomerMapper;
import com.defers.crm.customers.entity.Customer;
import com.defers.crm.customers.enums.CustomerType;
import com.defers.crm.customers.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@ExtendWith(value = MockitoExtension.class)
@Import(value = {AppConfiguration.class})
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerServiceImpl customerService;
    private List<Customer> customers;
    @Mock
    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        String id1 = "fa994eca-23b4-4cb2-abed-cf7d16fa3735";
        String id2 = "12ad88ea-7d35-4418-964e-01df00464439";

        Customer customer1 = Customer.builder()
                .id(id1)
                .name("partner1")
                .type(CustomerType.CORPORATE)
                .createdDate(LocalDateTime.of(2023, 1, 1, 17, 10))
                .build();

        Customer customer2 = Customer.builder()
                .id(id2)
                .name("partner2")
                .type(CustomerType.PHYSICAL)
                .createdDate(LocalDateTime.of(2023, 2, 5, 15, 20))
                .build();

        this.customers = List.of(customer1, customer2);
    }

    @Test
    void findDTOById() {
        String id1 = "fa994eca-23b4-4cb2-abed-cf7d16fa3735";
        Customer customer1 = getCustomer(id1);

        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(customer1.getId())
                .name(customer1.getName())
                .type(customer1.getType())
                .createdDate(customer1.getCreatedDate())
                .build();
        // given
        given(customerRepository.findById(id1))
                .willReturn(Optional.of(customer1));
        given(customerMapper.toDTO(customer1, new CustomerDTO()))
                .willReturn(customerDTO1);

        // when
        CustomerDTO customerDTO = customerService.findDTOById(id1);
        Assertions.assertThat(customerDTO)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", id1);
    }

    private Customer getCustomer(String id) {
        return customers.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().get();
    }
}
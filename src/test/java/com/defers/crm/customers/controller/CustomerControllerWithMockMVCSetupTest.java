package com.defers.crm.customers.controller;

import com.defers.crm.customers.controller.advice.ControllerAdvice;
import com.defers.crm.customers.dto.CustomerDTO;
import com.defers.crm.customers.enums.CustomerType;
import com.defers.crm.customers.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerWithMockMVCSetupTest {
    private MockMvc mvc;
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    @Autowired
    private MessageSource messageSource;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new ControllerAdvice(messageSource))
                .build();
    }

    @Test
    public void findAll() throws Exception {

        String id1 = "fa994eca-23b4-4cb2-abed-cf7d16fa3735";
        String id2 = "12ad88ea-7d35-4418-964e-01df00464439";
        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(id1)
                .name("partner1")
                .type(CustomerType.CORPORATE)
                .createdDate(LocalDateTime.of(2023, 1, 1, 17, 10))
                .build();
        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .id(id2)
                .name("partner2")
                .type(CustomerType.PHYSICAL)
                .createdDate(LocalDateTime.of(2023, 2, 5, 15, 20))
                .build();

        // given
        given(customerService.findAllDTO(0, 5))
                .willReturn(List.of(customerDTO1, customerDTO2));

        // when
        mvc.perform(
                MockMvcRequestBuilders.get("/v1/customers?pagenumber=0&numberonpage=5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void findById() throws Exception {

        String id1 = "fa994eca-23b4-4cb2-abed-cf7d16fa3735";
        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(id1)
                .name("partner1")
                .type(CustomerType.CORPORATE)
                .createdDate(LocalDateTime.of(2023, 1, 1, 17, 10))
                .build();

        // given
        given(customerService.findDTOById(id1))
                .willReturn(customerDTO1);

        // when
        mvc.perform(
                MockMvcRequestBuilders.get("/v1/customers/fa994eca-23b4-4cb2-abed-cf7d16fa3735")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

}

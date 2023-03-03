package com.defers.crm.customers.controller;

import com.defers.crm.customers.dto.CustomerDTO;
import com.defers.crm.customers.enums.CustomerType;
import com.defers.crm.customers.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMapAdapter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;

@AutoConfigureMockMvc
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    @Test
    void findAll() throws Exception {
        int pageNumber = 0;
        int numberOnPage = 10;
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

        Mockito.when(customerService.findAllDTO(pageNumber, numberOnPage))
                .thenReturn(List.of(customerDTO1, customerDTO2));

        Map<String, List<String>> params = new HashMap<>();
        params.put("pagenumber", List.of(String.valueOf(pageNumber)));
        params.put("numberonpage", List.of(String.valueOf(numberOnPage)));

        mvc.perform(MockMvcRequestBuilders.get(String
                .format("/v1/customers?pagenumber=%s&numberonpage=%s", pageNumber, numberOnPage)
        )
                .contentType(MediaType.APPLICATION_JSON).params(new MultiValueMapAdapter<>(params)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));

    }

    @Test
    void save() {
    }

    @Test
    void findById() throws Exception {
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
                MockMvcRequestBuilders.get("/v1/customers/" + id1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByName() {
    }
}
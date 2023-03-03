package com.defers.crm.customers.service;

import com.defers.crm.customers.dto.CustomerDTO;
import com.defers.crm.customers.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll(Integer pageNumber, Integer numberOnPage);
    List<CustomerDTO> findAllDTO(Integer pageNumber, Integer numberOnPage);
    Customer findById(String id);
    CustomerDTO findDTOById(String id);
    Customer findByName(String name);
    CustomerDTO findDTOByName(String name);
    Customer save(Customer customer);
    CustomerDTO saveDTO(CustomerDTO customer);
    Customer update(String id, Customer customer);
    void setDeleteById(String id);

}

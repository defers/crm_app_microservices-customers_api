package com.defers.crm.customers.service;

import com.defers.crm.customers.entity.Customer;

import java.awt.print.Pageable;
import java.util.List;

public interface CustomerService {
    List<Customer> findAll(Integer pageNumber, Integer numberOnPage);
    Customer findById(String id);
    Customer findByName(String name);
    Customer save(Customer customer);
    Customer update(String id, Customer customer);
    void setDeleteById(String id);

}

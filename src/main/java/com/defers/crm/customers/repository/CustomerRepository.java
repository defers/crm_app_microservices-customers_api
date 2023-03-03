package com.defers.crm.customers.repository;

import com.defers.crm.customers.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, String>, CrudRepository<Customer, String> {
    Optional<Customer> findByNameAndDeleted(String name, boolean deleted);
    Optional<Customer> findByName(String name);
}

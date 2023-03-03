package com.defers.crm.customers.controller;

import com.defers.crm.customers.entity.Customer;
import com.defers.crm.customers.enums.CustomerType;
import com.defers.crm.customers.exception.ConstraintErrorException;
import com.defers.crm.customers.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RequestMapping("v1/customers")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    ResponseEntity<List<Customer>> findAll(@RequestParam(name = "pagenumber", required = false) Integer pageNumber,
                           @RequestParam(name = "numberonpage", required = false) Integer numberOnPage) {
        return ResponseEntity.ok(customerService.findAll(pageNumber, numberOnPage));
    }

    @PostMapping
    Customer save(@RequestBody @Valid Customer customer, BindingResult bindingResult) {
        checkErrors(bindingResult);
        return customerService.save(customer);
    }

    private void checkErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ConstraintErrorException(bindingResult);
        }
    }

    @GetMapping("/{id}")
    Customer findById(@PathVariable String id) {
        return customerService.findById(id);
    }

    @GetMapping("/search")
    Customer findByName(@RequestParam String name) {
        return customerService.findByName(name);
    }
}

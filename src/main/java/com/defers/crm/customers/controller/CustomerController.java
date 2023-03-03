package com.defers.crm.customers.controller;

import com.defers.crm.customers.dto.CustomerDTO;
import com.defers.crm.customers.exception.ConstraintErrorException;
import com.defers.crm.customers.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CustomerDTO>> findAll(@RequestParam(name = "pagenumber", required = false) Integer pageNumber,
                                              @RequestParam(name = "numberonpage", required = false) Integer numberOnPage) {
        return ResponseEntity.ok(customerService.findAllDTO(pageNumber, numberOnPage));
    }

    @PostMapping
    CustomerDTO save(@RequestBody @Valid CustomerDTO customerDTO, BindingResult bindingResult) {
        checkErrors(bindingResult);
        return customerService.saveDTO(customerDTO);
    }

    private void checkErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ConstraintErrorException(bindingResult);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CustomerDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(customerService.findDTOById(id));
    }

    @GetMapping("/search")
    CustomerDTO findByName(@RequestParam String name) {
        return customerService.findDTOByName(name);
    }
}

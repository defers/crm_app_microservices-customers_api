package com.defers.crm.customers.service;

import com.defers.crm.customers.dto.CustomerDTO;
import com.defers.crm.customers.dto.dtomapper.CustomerMapper;
import com.defers.crm.customers.entity.Customer;
import com.defers.crm.customers.exception.EntityNotFoundException;
import com.defers.crm.customers.repository.CustomerRepository;
import com.defers.crm.customers.util.MessagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@RefreshScope
@Service
public class CustomerServiceImpl implements CustomerService {

    private final Clock clock;
    private final CustomerRepository customerRepository;
    private final MessageSource messageSource;
    private final Integer defaultPageNumber;
    private final Integer defaultNumberOnPage;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(Clock clock,
                               CustomerRepository customerRepository,
                               MessageSource messageSource,
                               @Value("${app.data.default-page-number}") Integer defaultPageNumber,
                               @Value("${app.data.default-number-on-page}") Integer defaultNumberOnPage,
                               CustomerMapper customerMapper) {
        this.clock = clock;
        this.customerRepository = customerRepository;
        this.messageSource = messageSource;
        this.defaultPageNumber = defaultPageNumber;
        this.defaultNumberOnPage = defaultNumberOnPage;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<Customer> findAll(Integer pageNumber,
                                  Integer numberOnPage) {
        if (Objects.isNull(pageNumber)) {
            pageNumber = defaultPageNumber;
        }
        if (Objects.isNull(numberOnPage)) {
            numberOnPage = defaultNumberOnPage;
        }
        Pageable pageable = PageRequest.of(pageNumber, numberOnPage);
        return customerRepository
                .findAll(pageable)
                .getContent();
    }

    @Override
    public List<CustomerDTO> findAllDTO(Integer pageNumber,
                                        Integer numberOnPage) {
        List<Customer> customers = findAll(pageNumber, numberOnPage);
        return customers.stream()
                .map(customer -> customerMapper.toDTO(customer, new CustomerDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> {
                            var msg = MessagesUtils.getFormattedMessage(
                                    messageSource.getMessage("exception.customernotfound",
                                            null,
                                            Locale.getDefault()), id);
                            return new EntityNotFoundException(msg);
                        }
                );
    }

    @Override
    public CustomerDTO findDTOById(String id) {
        Customer customer = findById(id);
        return customerMapper.toDTO(customer, new CustomerDTO());
    }

    @Override
    public Customer findByName(String name) {
        return customerRepository.findByNameAndDeleted(name, false)
                .orElseThrow(() -> {
                            var msg = MessagesUtils.getFormattedMessage(
                                    messageSource.getMessage("exception.customer-with-name-notfound",
                                            null,
                                            Locale.getDefault()), name);
                            return new EntityNotFoundException(msg);
                        }
                );
    }

    @Override
    public CustomerDTO findDTOByName(String name) {
        return customerMapper.toDTO(findByName(name), new CustomerDTO());
    }

    @Override
    public Customer save(Customer customer) {
        customer.setCreatedDate(LocalDateTime.now(clock));
        return customerRepository.save(customer);
    }

    @Override
    public CustomerDTO saveDTO(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO, new Customer());
        customer = save(customer);
        return customerMapper.toDTO(customer, new CustomerDTO());
    }

    @Override
    public Customer update(String id, Customer customer) {
        return null;
    }

    @Override
    public void setDeleteById(String id) {

    }
}

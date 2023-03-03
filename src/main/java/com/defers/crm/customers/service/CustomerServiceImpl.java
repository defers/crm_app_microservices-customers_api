package com.defers.crm.customers.service;

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

@RefreshScope
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private final Clock clock;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final MessageSource messageSource;
    private final Integer defaultPageNumber;
    private final Integer defaultNumberOnPage;

    public CustomerServiceImpl(Clock clock,
                               CustomerRepository customerRepository,
                               MessageSource messageSource,
                               @Value("${app.data.default-page-number}") Integer defaultPageNumber,
                               @Value("${app.data.default-number-on-page}") Integer defaultNumberOnPage) {
        this.clock = clock;
        this.customerRepository = customerRepository;
        this.messageSource = messageSource;
        this.defaultPageNumber = defaultPageNumber;
        this.defaultNumberOnPage = defaultNumberOnPage;
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
    public Customer save(Customer customer) {
        customer.setCreatedDate(LocalDateTime.now(clock));
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(String id, Customer customer) {
        return null;
    }

    @Override
    public void setDeleteById(String id) {

    }
}

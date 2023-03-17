package com.defers.crm.customers.service;

import com.defers.crm.customers.entity.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> findAll(Integer pageNumber, Integer numberOnPage);
    Currency findById(String id);
    //Currency findByName(String name); TODO
    Currency save(Currency currency);
}


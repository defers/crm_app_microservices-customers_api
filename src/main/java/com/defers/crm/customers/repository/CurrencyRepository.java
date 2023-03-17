package com.defers.crm.customers.repository;

import com.defers.crm.customers.entity.Currency;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CurrencyRepository extends PagingAndSortingRepository<Currency, String> {
}

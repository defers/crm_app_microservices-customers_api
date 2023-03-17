package com.defers.crm.customers.controller;

import com.defers.crm.customers.entity.Currency;
import com.defers.crm.customers.service.CurrencyService;
import com.defers.crm.customers.util.ValidatorUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/currencies")
@AllArgsConstructor
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Currency>> getAllByPage(@RequestParam(name = "pagenumber", required = false, defaultValue = "0") int pageNumber,
                                                @RequestParam(name = "numberonpage", required = false, defaultValue = "10") int numberOnPage) {
        List<Currency> currencies = currencyService.findAll(pageNumber, numberOnPage);
        return ResponseEntity.ok(currencies);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Currency> save(@RequestBody @Valid Currency currency, BindingResult bindingResult) {
        ValidatorUtils.checkErrors(bindingResult);
        Currency currencySaved = currencyService.save(currency);
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(currency.getId())
                        .toUri()
                ).body(currencySaved);
    }

}

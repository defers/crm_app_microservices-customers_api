package com.defers.crm.customers.service;

import com.defers.crm.customers.entity.Currency;
import com.defers.crm.customers.exception.EntityNotFoundException;
import com.defers.crm.customers.output.MessageSender;
import com.defers.crm.customers.repository.CurrencyRepository;
import com.defers.crm.customers.util.MessagesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final Clock clock;
    private final CurrencyRepository currencyRepository;
    private final MessageSource messageSource;
    private final Integer defaultPageNumber;
    private final Integer defaultNumberOnPage;
    private final MessageSender currencyKafkaSender;

    @Autowired
    public CurrencyServiceImpl(Clock clock,
                               CurrencyRepository currencyRepository,
                               MessageSource messageSource,
                               @Value("${app.data.default-page-number}") Integer defaultPageNumber,
                               @Value("${app.data.default-number-on-page}") Integer defaultNumberOnPage,
                               MessageSender currencyKafkaSender) {
        this.clock = clock;
        this.currencyRepository = currencyRepository;
        this.messageSource = messageSource;
        this.defaultPageNumber = defaultPageNumber;
        this.defaultNumberOnPage = defaultNumberOnPage;
        this.currencyKafkaSender = currencyKafkaSender;
    }

    @Override
    public List<Currency> findAll(Integer pageNumber, Integer numberOnPage) {
        if (Objects.isNull(pageNumber)) {
            pageNumber = defaultPageNumber;
        }
        if (Objects.isNull(numberOnPage)) {
            numberOnPage = defaultNumberOnPage;
        }
        Pageable pageable = PageRequest.of(pageNumber, numberOnPage);
        return currencyRepository
                .findAll(pageable)
                .getContent();
    }

    @Override
    public Currency findById(String id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> {
                            var msg = MessagesUtils.getFormattedMessage(
                                    messageSource.getMessage("exception.currency-not-found",
                                            null,
                                            Locale.getDefault()), id);
                            return new EntityNotFoundException(msg);
                        }
                );
    }

    @Transactional
    @Override
    public Currency save(Currency currency) {
        currency.setCreatedDate(LocalDateTime.now(clock));
        Currency currencySaved = currencyRepository.save(currency);
        currencyKafkaSender.send(currencySaved);
        return currencySaved;
    }
}

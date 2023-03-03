package com.defers.crm.customers.exception;

import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
public class ConstraintErrorException extends RuntimeException {
    private BindingResult bindingResult;

    public ConstraintErrorException(String message) {
        super(message);
    }
    public ConstraintErrorException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}

package com.defers.crm.customers.util;

import com.defers.crm.customers.exception.ConstraintErrorException;
import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;

@UtilityClass
public class ValidatorUtils {
    public static void checkErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ConstraintErrorException(bindingResult);
        }
    }
}

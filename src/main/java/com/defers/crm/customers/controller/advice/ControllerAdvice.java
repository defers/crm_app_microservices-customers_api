package com.defers.crm.customers.controller.advice;

import com.defers.crm.customers.dto.ApiError;
import com.defers.crm.customers.dto.ApiErrorResponse;
import com.defers.crm.customers.enums.ApiResponseStatus;
import com.defers.crm.customers.exception.ConstraintErrorException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@AllArgsConstructor
@Data
@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {ConstraintErrorException.class})
    public ResponseEntity<Object> exceptionConstraintHandler(ConstraintErrorException ex, WebRequest request) throws Exception {
        List<ApiError> apiErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors()
                .stream()
                .forEach(e -> fillApiErrors(e, apiErrors));

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .apiErrors(apiErrors)
                .message(messageSource.getMessage("exception.constraint-error", null, Locale.getDefault()))
                .apiResponseStatus(ApiResponseStatus.ERROR)
                .build();
        return handleExceptionInternal(ex, apiErrorResponse, HttpHeaders.EMPTY, HttpStatus.CONFLICT, request);
    }

    private void fillApiErrors(ObjectError e, List<ApiError> apiErrors) {
        String errorMsg = e.getDefaultMessage();
        for (Object argument: Objects.requireNonNull(e.getArguments())) {
            if (argument instanceof DefaultMessageSourceResolvable) {
                String fieldName = ((DefaultMessageSourceResolvable) argument).getCode();
                apiErrors.add(ApiError.builder()
                        .field(fieldName)
                        .errorMsg(errorMsg)
                        .build()
                );
            }
        }
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> exceptionHandler(RuntimeException ex, WebRequest request) throws Exception {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .message(ex.getMessage())
                .apiResponseStatus(ApiResponseStatus.ERROR)
                .build();
        return handleExceptionInternal(ex, apiErrorResponse, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request);
    }

}

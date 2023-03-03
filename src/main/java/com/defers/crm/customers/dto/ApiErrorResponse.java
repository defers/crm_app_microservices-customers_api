package com.defers.crm.customers.dto;

import com.defers.crm.customers.enums.ApiResponseStatus;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ApiErrorResponse {
    private final ApiResponseStatus apiResponseStatus;
    private final String message;
    private final List<ApiError> apiErrors;
}

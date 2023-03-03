package com.defers.crm.customers.dto;

import com.defers.crm.customers.enums.ApiResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private ApiResponseStatus apiResponseStatus;
    private String message;
    private List<ApiError> apiErrors;
}

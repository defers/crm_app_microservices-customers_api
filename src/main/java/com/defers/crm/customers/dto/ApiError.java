package com.defers.crm.customers.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
    private String field;
    private String errorMsg;
}

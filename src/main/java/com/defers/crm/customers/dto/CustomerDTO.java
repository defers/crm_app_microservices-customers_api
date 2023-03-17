package com.defers.crm.customers.dto;

import com.defers.crm.customers.enums.CustomerType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonPropertyOrder(value = {"id, type, name, createdDate"})
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private String id;
    @NotNull
    @NotBlank
    private String name;
    private CustomerType type;
    private LocalDateTime createdDate;
}

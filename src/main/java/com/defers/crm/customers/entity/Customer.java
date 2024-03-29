package com.defers.crm.customers.entity;

import com.defers.crm.customers.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RedisHash("Customer")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Customer extends BaseEntity {
    @Id
    private String id;
    @Indexed
    @NotNull
    @NotBlank
    private String name;
    private CustomerType type;
}

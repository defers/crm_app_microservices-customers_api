package com.defers.crm.customers.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RedisHash("Currency")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
public class Currency extends BaseEntity{
    @Id
    private String id;
    @Indexed
    @NotNull
    @NotBlank
    private String name;
    @Indexed
    private String code;
}

package com.defers.crm.customers.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseEntity implements Serializable {
    @Indexed
    private boolean deleted;
    @CreatedDate
    private LocalDateTime createdDate;
}

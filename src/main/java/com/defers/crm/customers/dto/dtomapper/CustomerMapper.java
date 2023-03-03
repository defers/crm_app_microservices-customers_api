package com.defers.crm.customers.dto.dtomapper;

import com.defers.crm.customers.dto.CustomerDTO;
import com.defers.crm.customers.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer, @MappingTarget CustomerDTO customerDTO);
    Customer toEntity(CustomerDTO customerDTO, @MappingTarget Customer customer);
}

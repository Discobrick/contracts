package com.example.contracts.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class ContractDTO {

    private Long contractId;
    private Long customerId;
    private String startDate;
    private String contractType;
    private String contractName;

    private CustomerDTO customer;
    private String customerType;
    private String customerFirstName;
    private String customerLastName;
    private String customerSsn;
}

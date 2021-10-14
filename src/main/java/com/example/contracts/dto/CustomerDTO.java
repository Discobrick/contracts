package com.example.contracts.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CustomerDTO {



    private Long customerId;
    private Long ssn;
    private String firstName;
    private String lastName;
    private String customerType;
    private List<ContractDTO> contracts;

}

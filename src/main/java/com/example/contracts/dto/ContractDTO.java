package com.example.contracts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
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

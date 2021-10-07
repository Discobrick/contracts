package com.example.contracts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {



    private Long customerId;
    private Long ssn;
    private String firstName;
    private String lastName;
    private String customerType;
    private List<ContractDTO> contracts;

}

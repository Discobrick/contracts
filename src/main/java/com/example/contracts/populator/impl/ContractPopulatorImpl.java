package com.example.contracts.populator.impl;

import com.example.contracts.dto.ContractDTO;
import com.example.contracts.dto.CustomerDTO;
import com.example.contracts.domain.Contract;
import com.example.contracts.domain.Customer;
import com.example.contracts.domain.enums.ContractType;
import com.example.contracts.populator.ContractPopulator;
import com.example.contracts.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ContractPopulatorImpl implements ContractPopulator {

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @Override
    public ContractDTO convertContractEntityToDTO(Contract source) {

        Customer customer = customerServiceImpl.getUserById(source.getCustomer().getId());
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerType(customer.getType().toString())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .customerId(customer.getId())
                .ssn(customer.getSsn())
                .build();

        ContractDTO target = ContractDTO.builder()
                .contractId(source.getId())
                .contractName(source.getName())
                .customerId(source.getCustomer().getId())
                .contractType(source.getContractType().toString())
                .startDate(source.getStartDate().toString())
                .customer(customerDTO)
                .build();

        return target;
    }

    @Override
    public void convertContractDtoToEntity(ContractDTO source, Contract target) throws ParseException {
        Date startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(source.getStartDate());
        target.setStartDate(startDate);
        target.setContractType(ContractType.valueOf(source.getContractType().toUpperCase()));

        Customer customer = customerServiceImpl.getUserById(source.getCustomerId());
        target.setCustomer(customer);
        if (source.getContractName().isEmpty()) {
            target.setName(customer.getFirstName() + customer.getLastName() + source.getStartDate().substring(9));
        } else {
            target.setName(source.getContractName());
        }

    }

}

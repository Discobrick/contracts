package com.example.contracts.populator;

import com.example.contracts.dto.ContractDTO;
import com.example.contracts.dto.CustomerDTO;
import com.example.contracts.models.Contract;
import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.service.ContractService;
import com.example.contracts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerPopulator {

    @Autowired
    ContractService contractService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ContractPopulator contractPopulator;

    public void convertCustomerEntityToDTO(Customer source, CustomerDTO target) {
        target.setCustomerId(source.getId());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setCustomerType(source.getType().toString());
        target.setSsn(source.getSsn());

        List<Contract> contracts = contractService.getContractsByCustomerId(source.getId());
        List<ContractDTO> contractDTOS = new ArrayList<>();
        contracts.forEach(contract -> {
            ContractDTO retreivedContract = new ContractDTO();
            contractPopulator.convertContractEntityToDTO(contract, retreivedContract);
            contractDTOS.add(retreivedContract);
        });
        target.setContracts(contractDTOS);
    }

    public void convertCustomerDtoToEntity(CustomerDTO source, Customer target) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setType(CustomerType.valueOf(source.getCustomerType().toUpperCase()));

    }

}

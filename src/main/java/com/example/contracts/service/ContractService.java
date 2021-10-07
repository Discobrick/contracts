package com.example.contracts.service;


import com.example.contracts.dto.ContractDTO;
import com.example.contracts.models.Contract;
import com.example.contracts.repositories.ContractRepository;
import com.example.contracts.repositories.ContractSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContractService {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    CustomerService customerService;


    public List<Contract> getAllContractsByFilter(ContractDTO contractDTO) {

        Specification<Contract> typeSpec = ContractSpecs.contractTypeEquals(contractDTO.getContractType());
        Specification<Contract> dateSpec = ContractSpecs.contractDateAfter(contractDTO.getStartDate());
        Specification<Contract> customerTypeSpec = ContractSpecs.contractCustomerTypeEquals(contractDTO.getCustomerType());
        Specification<Contract> cFirstNameSpec = ContractSpecs.contractCustomerFirstNameEquals(contractDTO.getCustomerFirstName());
        Specification<Contract> cLastNameSpec = ContractSpecs.contractCustomerLastNameEquals(contractDTO.getCustomerLastName());

        Specification<Contract> spec = Specification
                .where(typeSpec)
                .and(dateSpec)
                .and(customerTypeSpec)
                .and(cFirstNameSpec
                .and(cLastNameSpec));

        return contractRepository.findAll(spec);
    }

    public List<Contract> getContracts() {
        return contractRepository.findAll();
    }

    public List<Contract> getContractsByCustomerId(Long id) {
        return contractRepository.findByCustomerId(id);
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }


}

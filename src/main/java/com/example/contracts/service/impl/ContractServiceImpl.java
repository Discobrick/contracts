package com.example.contracts.service.impl;

import com.example.contracts.dto.ContractDTO;
import com.example.contracts.domain.Contract;
import com.example.contracts.repositories.ContractRepository;
import com.example.contracts.repositories.ContractSpecs;
import com.example.contracts.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    CustomerServiceImpl customerServiceImpl;

    @Override
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

    @Override
    public List<Contract> getContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

}

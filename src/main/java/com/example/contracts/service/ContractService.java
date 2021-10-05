package com.example.contracts.service;

import com.example.contracts.models.Contract;
import com.example.contracts.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService {
    @Autowired
    ContractRepository contractRepository;

    public List<Contract> getContracts(){
        return contractRepository.findAll();
    }



    public List<Contract> getContractsByCustomerName(String lastName){
        return getContracts().stream()
                .filter(contract -> contract.getCustomer().getLastName().contains(lastName))
                .collect(Collectors.toList());
    }

    public Contract createContract(Contract contract){

        return contractRepository.save(contract);
    }
}

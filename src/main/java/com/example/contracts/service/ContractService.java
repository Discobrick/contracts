package com.example.contracts.service;


import com.example.contracts.models.Contract;
import com.example.contracts.models.enums.ContractType;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ContractService {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    CustomerService customerService;

    public Contract getContractById(Long id){
       return contractRepository.getById(id);
    }

    public List<Contract> getContracts(){
        return contractRepository.findAll();
    }

    public List<Contract> getContractsByCustomerName(String firstName,String lastName) {
        return contractRepository.findByCustomerFirstNameOrCustomerLastName(firstName,lastName);
    }

    public List<Contract> getContractsByStartDate(Date date){
        return contractRepository.findByStartDateAfter(date);
    }

    public List<Contract> getContractsByType(ContractType contractType){
        return contractRepository.findByContractType(contractType);
    }

    public List<Contract> getContractsByCustomerType(CustomerType customerType){
        return contractRepository.findByCustomerType( customerType);
    }

    public List<Contract> getContractsByCustomerId(Long id){
        return contractRepository.findByCustomerId(id);
    }

    public Contract createContract(Contract contract){
        return contractRepository.save(contract);
    }




}

package com.example.contracts.repositories;

import com.example.contracts.models.Contract;
import com.example.contracts.models.enums.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;


public interface ContractRepository extends JpaRepository<Contract,Long> {
    List<Contract> findByCustomerFirstNameOrCustomerLastName(String firstName, String lastName);
    List<Contract> findByStartDateAfter(Date date);
    List<Contract> findByContractType(ContractType contractType);

}
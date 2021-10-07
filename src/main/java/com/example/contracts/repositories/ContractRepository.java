package com.example.contracts.repositories;

import com.example.contracts.models.Contract;
import com.example.contracts.models.enums.ContractType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Long>, JpaSpecificationExecutor<Contract> {

    List<Contract> findByStartDateAfter(Date date);
    List<Contract> findByContractType(ContractType contractType);
    List<Contract> findByCustomerId(Long customerId);
}
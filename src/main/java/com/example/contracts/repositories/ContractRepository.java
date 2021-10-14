package com.example.contracts.repositories;

import com.example.contracts.domain.Contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface ContractRepository extends JpaRepository<Contract,Long>, JpaSpecificationExecutor<Contract> {
}
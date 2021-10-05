package com.example.contracts.repositories;

import com.example.contracts.models.Contract;
import com.example.contracts.models.enums.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    List<Contract> findByCustomerFirstNameOrCustomerLastName(String firstName, String lastName);
    List<Contract> findByStartDateAfter(Date date);
}
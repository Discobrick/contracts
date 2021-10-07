package com.example.contracts.repositories;

import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer,Long > {
     List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
     List<Customer> findByFirstNameContainsAndLastNameContains(String firstName, String lastName);
     List<Customer> findByType(CustomerType customerType);
     Customer findDistinctBySsn(long ssn);
}

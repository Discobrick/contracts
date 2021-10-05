package com.example.contracts.repositories;

import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer,Long > {
     List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
     List<Customer> findByType(CustomerType customerType);
}

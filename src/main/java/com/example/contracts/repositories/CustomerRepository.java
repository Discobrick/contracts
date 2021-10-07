package com.example.contracts.repositories;

import com.example.contracts.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long > {
     Customer findDistinctBySsn(long ssn);
}

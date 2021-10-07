package com.example.contracts.service;


import com.example.contracts.models.Customer;
import com.example.contracts.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer getUserById(Long id) {
        return customerRepository.getById(id);
    }

    public Customer getCustomerBySSN(long ssn){
       return customerRepository.findDistinctBySsn(ssn);
    }
}

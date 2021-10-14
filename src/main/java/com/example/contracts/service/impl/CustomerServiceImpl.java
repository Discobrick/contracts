package com.example.contracts.service.impl;


import com.example.contracts.domain.Customer;
import com.example.contracts.repositories.CustomerRepository;
import com.example.contracts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer getUserById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer getCustomerBySSN(long ssn){
       return customerRepository.findDistinctBySsn(ssn);
    }
}

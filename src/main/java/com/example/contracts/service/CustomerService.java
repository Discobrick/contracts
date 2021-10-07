package com.example.contracts.service;


import com.example.contracts.dto.CustomerDTO;
import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer.setSsn(customerDTO.getSsn());
        newCustomer.setType(CustomerType.valueOf(customerDTO.getCustomerType().toUpperCase()));
        newCustomer.setFirstName(customerDTO.getFirstName());
        newCustomer.setLastName(customerDTO.getLastName());
        return customerRepository.save(newCustomer);
    }

    public Customer getUserById(Long id) {
        return customerRepository.getById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomerByFirstAndLastName(String firstName, String lastName) {
        return customerRepository.findByFirstNameContainsAndLastNameContains(firstName, lastName);
    }

    public List<Customer> getCustomerByType(CustomerType customerType) {
        return customerRepository.findByType(customerType);
    }

    public Customer getCustomerBySSN(CustomerDTO customerDTO){
       return customerRepository.findDistinctBySsn(customerDTO.getSsn());
    }
}

package com.example.contracts.controllers;


import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/api/customers")
    public List<Customer> getCustomerById(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/api/customer")
    public Customer getCustomerById(@RequestParam("id") String id){
        return customerService.getUserById(Long.valueOf(id));
    }

    @PostMapping(value ="/api/customer/create")
    public Customer createCustomer(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("type") String type){
          Customer newCustomer = new Customer(firstName,lastName, CustomerType.valueOf(type));
        return customerService.createCustomer(newCustomer);
    }

}

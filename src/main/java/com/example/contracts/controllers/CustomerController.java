package com.example.contracts.controllers;

import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.List;

@RestController
@RequestMapping(("/customer"))
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/all")
    public List<Customer> getCustomerByAll(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/id")
    public Customer getCustomerById(@RequestParam("id") String id){
        return customerService.getUserById(Long.valueOf(id));
    }

    @PostMapping(value ="/create")
    public Customer createCustomer(@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,@RequestParam("type") String type){
          Customer newCustomer = new Customer(firstName,lastName, CustomerType.valueOf(type));
        return customerService.createCustomer(newCustomer);
    }

    @GetMapping("/type")
    public List<Customer> getContractsByContractType(@RequestParam(name = "customerType") String customerType){
        return customerService.getCustomerByType(EnumUtils.findEnumInsensitiveCase(CustomerType.class,customerType));
    }

}

package com.example.contracts.controllers;

import com.example.contracts.dto.CustomerDTO;
import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.populator.CustomerPopulator;
import com.example.contracts.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(("/customer"))
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    CustomerPopulator customerPopulator;

    @GetMapping("/all")
    public Object getCustomerByAll() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> retreivedCustomers = new ArrayList<>();
        customers.forEach(customer -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerPopulator.convertCustomerEntityToDTO(customer,customerDTO);
            retreivedCustomers.add(customerDTO);
        });
        return  retreivedCustomers;
    }


    @GetMapping("/id")
    public Object getCustomerById(@RequestBody CustomerDTO customerDto) {
        CustomerDTO retreivedCustomer;
        if (null == customerDto.getCustomerId()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            Customer customer = customerService.getUserById(customerDto.getCustomerId());
            retreivedCustomer = new CustomerDTO();
            customerPopulator.convertCustomerEntityToDTO(customer, retreivedCustomer);
        }
        return retreivedCustomer;
    }

    @GetMapping("/type")
    public Object getCustomerByType(@RequestBody CustomerDTO customerDto) {
        if (customerDto.getCustomerType() == null) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            try {
                List<Customer> customers = customerService.getCustomerByType(CustomerType.valueOf(customerDto.getCustomerType().toUpperCase()));
                List<CustomerDTO> retreivedCustomers = new ArrayList<>();
                customers.forEach(customer -> {
                    CustomerDTO retreivedCustomer = new CustomerDTO();
                    customerPopulator.convertCustomerEntityToDTO(customer, retreivedCustomer);
                    retreivedCustomers.add(retreivedCustomer);
                });
                return retreivedCustomers;
            }catch (IllegalArgumentException e){
                LOG.error("Wrong customer type provided {}", customerDto.getCustomerType());
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/ssn")
    public Object getCustomerBySSN(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getSsn() == null) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }else {
            try {
                CustomerDTO retreivedCustomer = new CustomerDTO();
                Customer customer = customerService.getCustomerBySSN(customerDTO);
                customerPopulator.convertCustomerEntityToDTO(customer, retreivedCustomer);
                return retreivedCustomer;
            } catch (Exception e) {
                LOG.error("Error retrieving customer by ssn {}. Exception {}", customerDTO.getSsn(), e.getClass());
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/name")
    public Object getCustomerByFirstAndLastName(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getFirstName() == null && customerDTO.getLastName() == null) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }else {
            try {
                List<Customer> customers = customerService.getCustomerByFirstAndLastName(customerDTO.getFirstName(),customerDTO.getLastName());
                List<CustomerDTO> retreivedCustomers = new ArrayList<>();
                customers.forEach(customer -> {
                    CustomerDTO retreivedCustomer = new CustomerDTO();
                    customerPopulator.convertCustomerEntityToDTO(customer, retreivedCustomer);
                    retreivedCustomers.add(retreivedCustomer);
                });
                return retreivedCustomers;
            } catch (Exception e) {
                LOG.error("Error retrieving customer by ssn {}. Exception {}", customerDTO.getSsn(), e.getClass());
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object createCustomer(@RequestBody CustomerDTO customerDto) {
        if (customerDto.getFirstName() == null || customerDto.getLastName() == null || customerDto.getCustomerType() == null || customerDto.getSsn() == null) {
            if(getCustomerBySSN(customerDto)!= null){
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            customerService.createCustomer(customerDto);
            return getCustomerBySSN(customerDto);
        }
    }

}

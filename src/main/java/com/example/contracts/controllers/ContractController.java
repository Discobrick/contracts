package com.example.contracts.controllers;

import com.example.contracts.models.Contract;
import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.ContractType;
import com.example.contracts.service.ContractService;
import com.example.contracts.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class ContractController {

    private static final Logger LOG = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    ContractService contractService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/api/contract/all")
    public List<Contract> getAllContracts() {
        return contractService.getContracts();
    }

    @GetMapping("/api/contract")
    public List<Contract> getContractsByCustomerName(@RequestParam(name = "ln") String lastName) {
        return contractService.getContractsByCustomerName(lastName);
    }

    @PostMapping(value = "/api/contract/addContract")
    public Contract addContract(@RequestParam(value = "contractName", defaultValue = "", required = false) String contractName,
                                @RequestParam("customerId") String customerId,
                                @RequestParam("startDate") String date,
                                @RequestParam("contractType") String contractType){
        Contract newContract = new Contract();
        try {
            if (null != customerService.getUserById(Long.valueOf(customerId))) {
                Customer customer = customerService.getUserById(Long.valueOf(customerId));
                String newContractName = customer.getFirstName()+customer.getLastName()+contractType+ date.replace("/", "");
                newContract.setName(newContractName);
                newContract.setCustomer(customer);
                newContract.setStartDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
                newContract.setContractType(ContractType.valueOf(contractType));
            }
        } catch (Exception e) {
            LOG.error("User ID not provided for contract {}", contractName, e);
        }
        return contractService.createContract(newContract);
    }

}

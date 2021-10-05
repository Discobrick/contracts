package com.example.contracts.controllers;

import com.example.contracts.models.Contract;
import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.ContractType;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.service.ContractService;
import com.example.contracts.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.util.EnumUtils;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@RestController
@RequestMapping("/contract")
public class ContractController {

    private static final Logger LOG = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    ContractService contractService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    public List<Contract> getAllContracts() {
        return contractService.getContracts();
    }

    @GetMapping("/name")
    public List<Contract> getContractsByCustomerName(@RequestParam(name = "customerName") String name) {
        String[] names = name.split("\\s+");
        return contractService.getContractsByCustomerName(names[0],names[1]);
    }

    @GetMapping("/date")
    @JsonProperty("startDate")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    public List<Contract> getContractsByStartDate(@RequestBody Contract contract) throws ParseException {
        return contractService.getContractsByStartDate(contract.getStartDate());
    }

    @GetMapping("/type")
    public List<Contract> getContractsByContractType(@RequestBody Contract contract){
        return contractService.getContractsByType(contract.getContractType());
    }

    @GetMapping("/type/customer")
    public List<Contract> getContractsByCustomerType(@RequestBody Customer customer){
        return contractService.getContractsByCustomerType(EnumUtils.findEnumInsensitiveCase(CustomerType.class, customer.getType().toString()));
    }

    @GetMapping("/customer/id")
    public List<Contract> getContractsByCustomerId(@RequestBody Customer customer){
        return contractService.getContractsByCustomerId(customer.getId());
    }


    @PostMapping(value = "/addContract")
    public Contract addContract(@RequestBody Contract contract){
        Contract newContract = new Contract();
        try {
            if (null != customerService.getUserById(contract.getCustomer().getId())) {
                Customer customer = customerService.getUserById(contract.getCustomer().getId());
                String newContractName = customer.getFirstName()+customer.getLastName()+contract.getContractType();
                newContract.setName(newContractName);
                newContract.setCustomer(customer);
                newContract.setStartDate(contract.getStartDate());
                newContract.setContractType(ContractType.valueOf(contract.getContractType().toString()));
                return contractService.createContract(newContract);
            }
        } catch (Exception e) {
            LOG.error("User ID not provided for contract {}", contract.toString(), e);
        }
        return null;
    }

}

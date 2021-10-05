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
import org.yaml.snakeyaml.util.EnumUtils;

import java.sql.Array;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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
    public List<Contract> getContractsByStartDate(@RequestParam(name ="startDate") String startDate) throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
        return contractService.getContractsByStartDate(date);
    }

    @GetMapping("/type")
    public List<Contract> getContractsByContractType(@RequestParam(name = "contractType") String cotractType){
        return contractService.getContractsByType(EnumUtils.findEnumInsensitiveCase(ContractType.class,cotractType));
    }



    @PostMapping(value = "/addContract")
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
                return contractService.createContract(newContract);
            }
        } catch (Exception e) {
            LOG.error("User ID not provided for contract {}", contractName, e);
        }
        return null;
    }

}

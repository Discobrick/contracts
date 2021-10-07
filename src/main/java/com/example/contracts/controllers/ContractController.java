package com.example.contracts.controllers;

import com.example.contracts.dto.ContractDTO;
import com.example.contracts.dto.CustomerDTO;
import com.example.contracts.models.Contract;

import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.ContractType;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.populator.ContractPopulator;
import com.example.contracts.populator.CustomerPopulator;
import com.example.contracts.service.ContractService;
import com.example.contracts.service.CustomerService;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/contract")
public class ContractController {

    private static final Logger LOG = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    ContractService contractService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerPopulator customerPopulator;
    @Autowired
    ContractPopulator contractPopulator;

    @GetMapping("/all")
    public Object getAllContracts() {
        return convertContractListToDtoList(contractService.getContracts());
    }


    @GetMapping("/id")
    public Object getContractById(@RequestBody ContractDTO contractDTO) {
        if (null == contractDTO.getContractId()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            try {
                Contract contract = contractService.getContractById(contractDTO.getContractId());
                ContractDTO retrievedContract = new ContractDTO();
                contractPopulator.convertContractEntityToDTO(contract, retrievedContract);
                return retrievedContract;
            } catch (Exception e) {
                LOG.error("Contract with ID {} not found.  Exception {}", contractDTO.getContractId(), e.getClass());
            }
        }
        return null;
    }

    @GetMapping("/date")
    @JsonProperty("startDate")
    public Object getContractsByStartDate(@RequestBody ContractDTO contractDTO){
        if (contractDTO.getStartDate().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            try {
                Date startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(contractDTO.getStartDate());
                List<Contract> contract = contractService.getContractsByStartDate(startDate);
                List<ContractDTO> retreivedContracts = new ArrayList<>();
                contract.forEach(contract1 -> {
                    ContractDTO retreivedContract = new ContractDTO();
                    contractPopulator.convertContractEntityToDTO(contract1, retreivedContract);
                    retreivedContracts.add(retreivedContract);
                });
                return retreivedContracts;
            } catch (Exception e) {
                LOG.error("Error retrieving contract by start date {}. Exception {}", contractDTO.getStartDate(), e.getClass());
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/type")
    public Object getContractsByContractType(@RequestBody ContractDTO contractDTO) {
        if (contractDTO.getContractType().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        try {
            List<Contract> contract = contractService.getContractsByType(ContractType.valueOf(contractDTO.getContractType().toUpperCase()));
            List<ContractDTO> retreivedContracts = new ArrayList<>();
            contract.forEach(contract1 -> {
                ContractDTO retreivedContract = new ContractDTO();
                contractPopulator.convertContractEntityToDTO(contract1, retreivedContract);
                retreivedContracts.add(retreivedContract);
            });
            return retreivedContracts;
        } catch (Exception e) {
            LOG.error("ContractDTO doesn't have the correct type, current type {}.", contractDTO.getContractType());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/type")
    public Object getContractsByCustomerType(@RequestBody CustomerDTO customerDTO) {
        if (customerDTO.getCustomerType().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        try {
            List<Customer> customers = customerService.getCustomerByType(CustomerType.valueOf(customerDTO.getCustomerType().toUpperCase()));
            return getContractsFromCustomerList(customers);
        } catch (Exception e) {
            LOG.error("Incorrect ID in customerDTO, current id {}.", customerDTO.getCustomerId());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/ssn")
    public Object getContractsByCustomerId(@RequestBody CustomerDTO customerDTO) {
        if (customerDTO.getSsn() == null) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        try {
            Customer customer = customerService.getCustomerBySSN(customerDTO);
            return convertContractListToDtoList(customer.getContracts());
        } catch (Exception e) {
            LOG.error("Incorrect SSN in customerDTO, current ssn {}.", customerDTO.getSsn());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/name")
    public Object getContractsByCustomerName(@RequestBody CustomerDTO customerDTO) {
        if (customerDTO.getFirstName().isEmpty() || customerDTO.getLastName().isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        try {
            List<Customer> customers = customerService.getCustomerByFirstAndLastName(customerDTO.getFirstName(), customerDTO.getLastName());
            return getContractsFromCustomerList(customers);
        } catch (Exception e) {
            LOG.error("Incorrect First or Last name in customerDTO, {}.", customerDTO);
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/create")
    public Contract addContract(@RequestBody ContractDTO contractDTO) {
        try {
            if (contractDTO != null && null != customerService.getUserById(contractDTO.getCustomerId())) {
                Contract newContract = new Contract();
                contractPopulator.convertContractDtoToEntity(contractDTO, newContract);
                return contractService.createContract(newContract);
            }

        } catch (Exception e) {
            LOG.error("Incorrect information in ContractDTO: {}. Exception {}", contractDTO, e.getClass());
        }
        return null;
    }

    private Object getContractsFromCustomerList(List<Customer> customers) {
        List<Contract> contracts = new ArrayList<>();
        customers.forEach(customer ->
                contracts.addAll(customer.getContracts()));
        List<ContractDTO> retreivedContracts = new ArrayList<>();
        contracts.forEach(contract -> {
            ContractDTO retreivedContract = new ContractDTO();
            contractPopulator.convertContractEntityToDTO(contract, retreivedContract);
            retreivedContracts.add(retreivedContract);
        });
        return retreivedContracts;
    }

    private Object convertContractListToDtoList(List<Contract> contracts2) {
        List<ContractDTO> retreivedContracts = new ArrayList<>();
        contracts2.forEach(contract -> {
            ContractDTO contractDTO = new ContractDTO();
            contractPopulator.convertContractEntityToDTO(contract, contractDTO);
            retreivedContracts.add(contractDTO);
        });
        return retreivedContracts;
    }
}

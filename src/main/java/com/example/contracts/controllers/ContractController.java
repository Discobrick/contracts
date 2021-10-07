package com.example.contracts.controllers;

import com.example.contracts.dto.ContractDTO;
import com.example.contracts.dto.CustomerDTO;
import com.example.contracts.models.Contract;

import com.example.contracts.models.Customer;
import com.example.contracts.populator.ContractPopulator;
import com.example.contracts.populator.CustomerPopulator;
import com.example.contracts.service.ContractService;
import com.example.contracts.service.CustomerService;

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

import java.util.ArrayList;
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

    @GetMapping("/")
    public Object getContractByMultiple(@RequestBody ContractDTO contractDTO){
        try{
            return convertContractListToDtoList(contractService.getAllContractsByFilter(contractDTO));
        }catch (Exception e){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/create")
    public Object addContract(@RequestBody ContractDTO contractDTO) {
        try {
            if (contractDTO != null && null != customerService.getCustomerBySSN(Long.parseLong(contractDTO.getCustomerSsn()))) {
                Contract newContract = new Contract();
                contractPopulator.convertContractDtoToEntity(contractDTO, newContract);
                return contractService.createContract(newContract);
            }else{
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            LOG.error("Incorrect information in ContractDTO: {}. Exception {}", contractDTO, e.getClass());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/customer/ssn")
    public Object getContractsByCustomerId(@RequestBody CustomerDTO customerDTO) {
        if (customerDTO.getSsn() == null) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        try {
            Customer customer = customerService.getCustomerBySSN(customerDTO.getSsn());
            return convertContractListToDtoList(customer.getContracts());
        } catch (Exception e) {
            LOG.error("Incorrect SSN in customerDTO, current ssn {}.", customerDTO.getSsn());
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}

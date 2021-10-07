package com.example.contracts.populator;

import com.example.contracts.dto.ContractDTO;
import com.example.contracts.dto.CustomerDTO;
import com.example.contracts.models.Contract;
import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.ContractType;
import com.example.contracts.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class ContractPopulator {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerPopulator customerPopulator;

    public void convertContractEntityToDTO(Contract source, ContractDTO target){
        target.setContractId(source.getId());
        target.setContractName(source.getName());
        target.setCustomerId(source.getCustomer().getId());
        target.setContractType(source.getContractType().toString());
        target.setStartDate(source.getStartDate().toString());

        Customer customer = customerService.getUserById(source.getCustomer().getId());
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerType(customer.getType().toString());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setCustomerId(customerDTO.getCustomerId());
        customerDTO.setSsn(customer.getSsn());
        customerDTO.setCustomerId(customer.getId());

        target.setCustomer(customerDTO);
    }

    public void convertContractDtoToEntity(ContractDTO source, Contract target) throws ParseException {
        Date startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(source.getStartDate());
        target.setStartDate(startDate);
        target.setContractType(ContractType.valueOf(source.getContractType().toUpperCase()));

        Customer customer = customerService.getUserById(source.getCustomerId());
        target.setCustomer(customer);
        if(source.getContractName().isEmpty()){
            target.setName(customer.getFirstName()+customer.getLastName()+source.getStartDate().substring(9));
        }else{
            target.setName(source.getContractName());
        }

    }

}

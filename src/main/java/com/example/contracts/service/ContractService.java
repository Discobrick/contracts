package com.example.contracts.service;

import com.example.contracts.dto.ContractDTO;
import com.example.contracts.domain.Contract;

import java.util.List;

public interface ContractService {
    List<Contract> getAllContractsByFilter(ContractDTO contractDTO);
    List<Contract> getContracts();
    Contract createContract(Contract contract);
}

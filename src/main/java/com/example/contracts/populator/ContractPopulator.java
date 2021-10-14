package com.example.contracts.populator;

import com.example.contracts.dto.ContractDTO;
import com.example.contracts.domain.Contract;

import java.text.ParseException;

public interface ContractPopulator {
    void convertContractDtoToEntity(ContractDTO source, Contract target) throws ParseException;
    ContractDTO convertContractEntityToDTO(Contract source);
}

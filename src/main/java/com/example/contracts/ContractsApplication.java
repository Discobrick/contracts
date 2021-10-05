package com.example.contracts;

import com.example.contracts.models.Contract;
import com.example.contracts.models.Customer;
import com.example.contracts.models.enums.ContractType;
import com.example.contracts.models.enums.CustomerType;
import com.example.contracts.repositories.ContractRepository;
import com.example.contracts.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class ContractsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContractsApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(CustomerRepository customerRepo, ContractRepository contractRepo){
		return args -> {
			Customer c1 = customerRepo.save(new Customer("Bilbo","Baggins", CustomerType.PRIVATE));
			Customer c2 = customerRepo.save(new Customer("Frodo","Baggins", CustomerType.PRIVATE));
			Customer c3 = customerRepo.save(new Customer("Gandalf","DaGray", CustomerType.BUSINESS));
			Customer c4 = customerRepo.save(new Customer("Gimli","And-my-axe", CustomerType.BUSINESS));
			contractRepo.save(new Contract("GasContractc1",new Date(),c1, ContractType.GAS));
			contractRepo.save(new Contract("ElectricityContractc2",new Date(),c2,ContractType.ELECTRICITY));
			contractRepo.save(new Contract("ElectricityContractc3",new Date(),c3,ContractType.ELECTRICITY));
			contractRepo.save(new Contract("GasContractc3",new Date(),c3, ContractType.GAS));
			contractRepo.save(new Contract("AllrounderContract4",new Date(),c4, ContractType.GAS_AND_ELECTRICITY));

		};
	}

}

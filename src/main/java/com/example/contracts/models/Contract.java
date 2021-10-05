package com.example.contracts.models;

import com.example.contracts.models.enums.ContractType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date startDate;

    @ManyToOne
    private Customer customer;

    @Enumerated(value = EnumType.STRING)
    private ContractType contractType;

    public Contract(String name, Date startDate, Customer customer, ContractType contractType) {
        this.name = name;
        this.startDate = startDate;
        this.customer = customer;
        this.contractType = contractType;
    }
}

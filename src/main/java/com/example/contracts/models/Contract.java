package com.example.contracts.models;

import com.example.contracts.models.enums.ContractType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Date startDate;

    @ManyToOne
    @JsonManagedReference
    private Customer customer;

    @Enumerated(value = EnumType.STRING)
    private ContractType contractType;

    public Contract(String name, Date startDate, Customer customer, ContractType contractType) {
        this.name = name;
        this.startDate = startDate;
        this.customer = customer;
        this.contractType = contractType;
    }

    public Contract() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }


}

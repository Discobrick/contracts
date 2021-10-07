package com.example.contracts.models;

import com.example.contracts.models.enums.ContractType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

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

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", customer=" + customer +
                ", contractType=" + contractType +
                '}';
    }

    public Contract(Long id, String name, Date startDate, Customer customer, ContractType contractType) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.customer = customer;
        this.contractType = contractType;
    }

    public Contract() {
    }

    public Contract(String name, Date startDate, Customer customer, ContractType contractType) {
        this.name = name;
        this.startDate = startDate;
        this.customer = customer;
        this.contractType = contractType;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }
}

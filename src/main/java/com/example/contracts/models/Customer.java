package com.example.contracts.models;

import com.example.contracts.models.enums.CustomerType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Contract> contracts;

    @Enumerated(value = EnumType.STRING)
    private CustomerType type;

    public Customer(String firstName, String lastName, CustomerType customerType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = customerType;

    }
}

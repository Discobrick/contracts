package com.example.contracts.repositories;

import com.example.contracts.domain.Contract;
import com.example.contracts.domain.enums.ContractType;
import com.example.contracts.domain.enums.CustomerType;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ContractSpecs {

    private ContractSpecs(){
        throw new IllegalStateException("Utility class");
    }

    public static final String CUSTOMER = "customer";

    public static Specification<Contract> contractTypeEquals(String type){
        return (root, query, criteriaBuilder) ->
                type == null ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("contractType"),ContractType.valueOf(type.toUpperCase()));

    }

    public static Specification<Contract> contractDateAfter(String date){
        return (root, query, criteriaBuilder) ->
        {
            try {
                return date == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThan(root.get("startDate"),new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    public static Specification<Contract> contractCustomerTypeEquals(String customerType){
        return (root, query, criteriaBuilder) ->
                customerType == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(CUSTOMER).get("type"),CustomerType.valueOf(customerType.toUpperCase()));
    }

    public static Specification<Contract> contractCustomerFirstNameEquals(String firstName){
        return (root, query, criteriaBuilder) ->
                firstName == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(CUSTOMER).get("firstName"),firstName);
    }

    public static Specification<Contract> contractCustomerLastNameEquals(String lastName){
        return (root, query, criteriaBuilder) ->
                lastName == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(CUSTOMER).get("lastName"),lastName);
    }
}

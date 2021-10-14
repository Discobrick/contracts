package com.example.contracts.service;

import com.example.contracts.domain.Customer;

public interface CustomerService {
     Customer getUserById(Long id);
     Customer getCustomerBySSN(long ssn);
}

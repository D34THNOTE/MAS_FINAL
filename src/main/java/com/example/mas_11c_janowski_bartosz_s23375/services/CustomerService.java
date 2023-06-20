package com.example.mas_11c_janowski_bartosz_s23375.services;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Customer;
import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Employee;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.CustomerRepository;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PersonRepository personRepository;

    public void saveCustomer(Customer customer) {
        if(customer == null) throw new IllegalArgumentException("Passed customer is null");

        if(customer.getOwner() == null) throw new IllegalArgumentException("Customer has to have an associated person with it");

        if(!personRepository.existsById(customer.getOwner().getIdPerson()))
            throw new IllegalArgumentException("Person associated with this customer isn't saved in the database");

        customerRepository.save(customer);
    }

}

package com.example.mas_11c_janowski_bartosz_s23375.services;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Customer;
import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Employee;
import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Person;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final EmployeeService employeeService;

    private final CustomerService customerService;


    /**
     * Saves or updates a Person entity in the system, maintains the OR constraint to ensure that a Person has either an associated Customer, Employee or both.
     *
     * @param person the Person to be saved or updated.
     * @throws IllegalArgumentException if the passed person is null or if the person has neither an Employee nor a Customer association.
     */
    public void savePerson(Person person) {
        // implementation of the OR constraint between Customer and Employee(a person can have both, but can't have neither)
        if (person == null) {
            throw new IllegalArgumentException("Passed person is null");
        }

        if (person.getEmployee() == null && person.getCustomer() == null) {
            throw new IllegalArgumentException("Person must have at least one association (Employee or Customer)");
        }

        if(person.getIdPerson() == null || !personRepository.existsById(person.getIdPerson())) {
            Employee tempEmp = null;
            Customer tempCust = null;
            if(person.getEmployee() != null)  {
                tempEmp = person.getEmployee();
                person.setEmployee(null);
            }
            if(person.getCustomer() != null) {
                tempCust = person.getCustomer();
                person.setCustomer(null);
            }

            personRepository.save(person);

            if(tempEmp != null) {
                employeeService.saveEmployee(tempEmp);
                person.setEmployee(tempEmp);
            }

            if(tempCust != null) {
                customerService.saveCustomer(tempCust);
                person.setCustomer(tempCust);
            }

            personRepository.save(person);
        }
        else {
            if(person.getEmployee() != null) {
                employeeService.saveEmployee(person.getEmployee());
            }

            if(person.getCustomer() != null) {
                customerService.saveCustomer(person.getCustomer());
            }

            personRepository.save(person);
        }

    }

}

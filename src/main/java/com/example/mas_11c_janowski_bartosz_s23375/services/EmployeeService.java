package com.example.mas_11c_janowski_bartosz_s23375.services;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Employee;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.EmployeeRepository;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PersonRepository personRepository;

    public void saveEmployee(Employee employee) {
        if(employee == null) throw new IllegalArgumentException("Passed employee is null");

        if(employee.getOwner() == null) throw new IllegalArgumentException("Employee has to have an associated person with it");

        if(!personRepository.existsById(employee.getOwner().getIdPerson()))
            throw new IllegalArgumentException("Person associated with this employee isn't saved in the database");

        if(!employee.isHourlyRateValid()) throw new IllegalArgumentException("Hourly rate of the Employee is invalid");

        if(!employee.isFireDateAfterHireDate()) throw new IllegalArgumentException("Employee's fire date is invalid");


        employeeRepository.save(employee);
    }

    public void changeHourlyRate(Employee employee, Double newRate) {
        if(employee == null) throw new IllegalArgumentException("Passed employee is null");
        if(newRate == null) throw new IllegalArgumentException("Passed new rate is null");

        if(newRate < Employee.getMinimumHourlyRate()) throw new IllegalArgumentException("The new rate is below the allowed minimum hourly late");
        if(newRate > employee.getHourlyRate() * 1.1) throw new IllegalArgumentException("The hourly rate cannot be raised by more than 10%");

        employee.setHourlyRate(newRate);
    }

}

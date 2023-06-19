package com.example.mas_11c_janowski_bartosz_s23375.repositories;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e JOIN e.owner o WHERE o.dateOfBirth = :dateOfBirth AND o.firstName = :firstName AND o.lastName = :lastName")
    List<Employee> findEmployeesByDOBFirstLast(LocalDate dateOfBirth, String firstName, String lastName);

}

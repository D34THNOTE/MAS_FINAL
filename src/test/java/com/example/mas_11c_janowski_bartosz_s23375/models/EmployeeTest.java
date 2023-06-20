package com.example.mas_11c_janowski_bartosz_s23375.models;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Employee;
import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Person;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StoreAddress;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.EmployeeRepository;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EmployeeTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PersonRepository personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    Person person1, person2;

    MusicStore musicStore;
    Employee employee1, employee2;

    @BeforeEach
    public void initializeData() {
        musicStore = MusicStore.builder()
                .storeName("Test music store")
                .floorsNumber(2)
                .storeAddress(new StoreAddress("ul.Cyfrowa", "68b", "Warsaw", "02-245", "Poland"))
                .build();

        person1 = Person.builder()
                .firstName("sddf")
                .lastName("fef")
                .dateOfBirth(LocalDate.of(1995, 10, 23))
                .phoneNumbers(Set.of("674835735", "235964732", "486346948"))
                .build();

        person2 = Person.builder()
                .firstName("sddf")
                .lastName("fef")
                .dateOfBirth(LocalDate.of(1993, 9, 17))
                .phoneNumbers(Set.of("457068424", "689567294", "5479674754"))
                .build();

        employee1 = Employee.builder()
                .owner(person1)
                .hireDate(LocalDate.of(2019, 9, 12))
                .hourlyRate(12.0)
                .isManager(true)
                .worksIn(musicStore)
                .build();

        employee2 = Employee.builder()
                .owner(person2)
                .hireDate(LocalDate.of(2020, 9, 12))
                .hourlyRate(13.0)
                .isManager(false)
                .worksIn(musicStore)
                .build();
    }

    @Test
    public void testFireDate() {
        assertThrows(IllegalArgumentException.class, () -> employee1.setFireDate(LocalDate.of(2019, 9, 11)));
    }

}

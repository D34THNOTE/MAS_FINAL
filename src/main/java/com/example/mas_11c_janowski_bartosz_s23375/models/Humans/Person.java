package com.example.mas_11c_janowski_bartosz_s23375.models.Humans;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPerson;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 150, message = "First name has to be between 2 and 150 characters long")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 150, message = "Last name has to be between 2 and 150 characters long")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    // OR constraint between Employee and Customer implemented on service layer or as a custom validator
    @OneToOne(mappedBy = "owner", cascade = CascadeType.REMOVE, orphanRemoval = true, optional = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.REMOVE, orphanRemoval = true, optional = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    @ElementCollection
    @CollectionTable(name = "person_phone_numbers", joinColumns = @JoinColumn(name = "id_person"))
    @Size(min = 1, message = "At least one phone number is required")
    private Set<String> phoneNumbers = new HashSet<>();

    // derived attribute "age"
    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears();
    }
}

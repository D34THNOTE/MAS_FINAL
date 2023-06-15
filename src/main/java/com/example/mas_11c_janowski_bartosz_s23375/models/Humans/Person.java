package com.example.mas_11c_janowski_bartosz_s23375.models.Humans;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPerson;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 150)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 150)
    private String lastName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @ElementCollection
    @CollectionTable(name = "person_phone_numbers", joinColumns = @JoinColumn(name = "id_person"))
    @Builder.Default
    @Size(min = 1, message = "At least one phone number is required")
    private Set<String> phoneNumbers = new HashSet<>();

    // derived attribute "age"
    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears();
    }
}

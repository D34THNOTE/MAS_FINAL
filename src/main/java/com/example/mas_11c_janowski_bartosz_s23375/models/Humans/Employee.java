package com.example.mas_11c_janowski_bartosz_s23375.models.Humans;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEmployee;

    @OneToOne(cascade = CascadeType.MERGE) //no cascade here or MERGE
    @NotNull(message = "Person owner class is required")
    @JoinColumn(name = "id_person")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person owner;

    @NotNull(message = "Hourly rate is required")
    @Min(10)
    private Double hourlyRate;

    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;

    private LocalDate fireDate;

    @NotNull(message = "Managerial status is required")
    private boolean isManager;

    @NotNull
    private static Double minimumHourlyRate = 10.0;

    @AssertTrue(message = "Hourly rate must be at least 10.00")
    private boolean isHourlyRateValid() {
        return hourlyRate >= minimumHourlyRate;
    }
}

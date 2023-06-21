package com.example.mas_11c_janowski_bartosz_s23375.models.Humans;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
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

    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull(message = "Person owner class is required")
    @JoinColumn(name = "id_person")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person owner;

    @NotNull(message = "Hourly rate is required")
    @Min(value = 0, message = "Hourly rate cannot be a negative number")
    @Max(value = 1000, message = "Maximum hourly rate is 1000")
    private Double hourlyRate;

    public void setHourlyRate(Double hourlyRate) {
        if(hourlyRate == null) throw new IllegalArgumentException("Hourly rate is required");

        if(hourlyRate < minimumHourlyRate) throw new IllegalArgumentException("Minimum hourly rate is " + minimumHourlyRate);

        if(this.hourlyRate != null && this.hourlyRate * 1.1 < hourlyRate) throw new IllegalArgumentException("Hourly rate cannot be raised by more than 10%");

        if(hourlyRate > 1000) throw new IllegalArgumentException("Hourly rate cannot be bigger than 1000");

        this.hourlyRate = hourlyRate;
    }

    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;

    private LocalDate fireDate;

    public void setFireDate(LocalDate fireDate) {
        if(fireDate != null && hireDate.isAfter(fireDate)) throw new IllegalArgumentException("Fire date cannot be a date before the date of hiring the employee");

        this.fireDate = fireDate;
    }

    @NotNull(message = "Managerial status is required")
    private boolean isManager;

    @NotNull
    private static Double minimumHourlyRate = 10.0;

    public static Double getMinimumHourlyRate() {
        return minimumHourlyRate;
    }

    @ManyToOne
    @JoinColumn(name = "id_music_store")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MusicStore worksIn;

    /**
     * Checks if the hourly rate of the Employee is valid.
     *
     * @return true if the hourly rate is valid and greater than or equal to the minimum hourly rate, false otherwise
     * @throws IllegalArgumentException if the hourly rate is null
     */
    public boolean isHourlyRateValid() {
        if(hourlyRate == null) throw new IllegalArgumentException("Hourly rate is required");

        return hourlyRate >= minimumHourlyRate;
    }

    /**
     * Checks if the fire date of the Employee is valid.
     *
     * @return true if the fire date is after the hire date or if the fire date is null, false otherwise, also if the hire date is null.
     */

    public boolean isFireDateAfterHireDate() {
        if(fireDate == null) {
            return true;
        }
        if(hireDate != null) {
            if(fireDate != null) return fireDate.isAfter(hireDate);
            return true;
        }

        return false;
    }
}

package com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.InstrumentImplementations;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Drum extends Instrument {
    @NotNull(message = "Drum size is required")
    @Min(0)
    private Double drumSizeInInches;

    @NotNull(message = "Height of the drum is required")
    @Min(0)
    private Integer heightInMillimeters;

    @NotNull(message = "Drum type is required")
    @Enumerated(EnumType.STRING)
    private DrumType drumType;
}

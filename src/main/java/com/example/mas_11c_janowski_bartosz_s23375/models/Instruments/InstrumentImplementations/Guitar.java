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
public class Guitar extends Instrument {
    @NotNull(message = "The number of strings of the guitar is required")
    @Min(0)
    private Integer numberOfStrings;

    @NotNull(message = "Guitar type is required")
    @Enumerated(EnumType.STRING)
    private GuitarType guitarType;

    @NotNull(message = "Height of the guitar is required")
    @Min(0)
    private Integer heightInMillimeters;

    @NotNull(message = "Width of the guitar is required")
    @Min(0)
    private Integer widthInMillimeters;

    @NotNull(message = "Depth of the guitar is required")
    @Min(0)
    private Integer depthInMillimeters;
}

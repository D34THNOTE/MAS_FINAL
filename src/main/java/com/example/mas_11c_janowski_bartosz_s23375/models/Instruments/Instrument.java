package com.example.mas_11c_janowski_bartosz_s23375.models.Instruments;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Materials.Metal;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Materials.Wood;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Purchase;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Stock;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idInstrument;

    @NotBlank(message = "Model name is required")
    @Size(min = 1, max = 300, message = "Model's name has to be between 1 and 300 characters long")
    @Column(unique = true) // ensures uniqueness of modelName
    private String modelName;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Instrument's price cannot be smaller than 0")
    @Max(value = 9999999, message = "Instrument's price cannot be bigger than 9 999 999")
    private Double price;

    @NotNull(message = "Instrument status is required")
    @Enumerated(EnumType.STRING) // EnumType defines that instrumentStatus is stored in the database as a string
    private InstrumentStatus instrumentStatus;

    @OneToMany(mappedBy = "instrument", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Stock> stocks = new HashSet<>();

    // XOR constraint between Wood and Metal should be implemented in service layer or as a custom validator
    @OneToOne(mappedBy = "instrument", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Wood wood;

    @OneToOne(mappedBy = "instrument", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Metal metal;

    @OneToMany(mappedBy = "instrument", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Purchase> purchases = new ArrayList<>();
}

package com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Materials;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Wood {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idWoodMaterial;

    @NotNull(message = "Type of wood is required")
    @Enumerated(EnumType.STRING)
    private WoodType woodType;

    @NotNull(message = "Applied finish type is required")
    @Enumerated(EnumType.STRING)
    private WoodFinishType woodFinishType;

    @OneToOne(cascade = CascadeType.MERGE)
    @NotNull(message = "Instrument owner class is required")
    @JoinColumn(name = "ID_INSTRUMENT")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Instrument instrument;
}

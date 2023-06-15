package com.example.mas_11c_janowski_bartosz_s23375.models.Humans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCustomer;

    @OneToOne(cascade = CascadeType.ALL) //TODO is this correct?
    @NotNull
    private Person owner;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private ShippingAddress shippingAddress;
}

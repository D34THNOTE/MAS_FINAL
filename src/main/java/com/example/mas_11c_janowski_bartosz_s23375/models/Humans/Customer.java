package com.example.mas_11c_janowski_bartosz_s23375.models.Humans;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCustomer;

    @NotNull(message = "Loyalty points are required")
    @Min(0)
    private Integer loyaltyPoints;

    @OneToOne(cascade = CascadeType.MERGE) //no cascade here or MERGE
    @NotNull(message = "Person owner class is required")
    @JoinColumn(name = "id_person")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Person owner;

    @NotNull(message = "Shipping address is required")
    @Embedded
    private ShippingAddress shippingAddress;

    // use an annotation for sorting Purchases or write a query(will need a List, not a Set, of Purchases here for the association)
}

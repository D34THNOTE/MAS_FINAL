package com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Customer;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPurchase;

    @NotNull(message = "Time of creating the purchase is required")
    private LocalDateTime purchaseDate;

    @NotNull(message = "Purchase status is required")
    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;

    @ManyToOne
    @JoinColumn(name = "id_instrument", nullable = false)
    @NotNull(message = "Instrument included in the purchase is required")
    private Instrument instrument;

    @ManyToOne
    @JoinColumn(name = "id_customer", nullable = false)
    @NotNull(message = "Customer included in the purchase is required")
    private Customer customer;
}

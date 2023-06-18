package com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_storage_room", "id_instrument"})
})
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idStock;

    @Min(value = 0, message = "Stock number cannot be smaller than 0")
    private Integer stockNumber;
    public void setStockNumber(Double stockNumber) {
        if (stockNumber != null) {
            this.stockNumber = (int) Math.round(stockNumber);
        }
    }

    @ManyToOne
    @JoinColumn(name = "id_storage_room", nullable = false)
    @NotNull(message = "Storage room is required")
    private StorageRoom storageRoom;

    @ManyToOne
    @JoinColumn(name = "id_instrument", nullable = false)
    @NotNull(message = "Instrument is required")
    private Instrument instrument;
}

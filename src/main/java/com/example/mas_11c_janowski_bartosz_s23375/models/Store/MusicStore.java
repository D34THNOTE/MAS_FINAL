package com.example.mas_11c_janowski_bartosz_s23375.models.Store;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.ShippingAddress;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MusicStore {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMusicStore;

    @NotNull(message = "Store name is required")
    @Size(min = 2, max = 500)
    private String storeName;

    @NotNull(message = "The number of floors is required")
    @Min(value = 1, message = "The number of floors cannot be smaller than 1")
    private Integer floorsNumber;

    @NotNull(message = "Store's address is required")
    @Embedded
    private StoreAddress storeAddress;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<StorageRoom> storageRooms = new HashSet<>();
}

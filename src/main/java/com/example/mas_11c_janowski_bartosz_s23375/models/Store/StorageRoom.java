package com.example.mas_11c_janowski_bartosz_s23375.models.Store;

import com.example.mas_11c_janowski_bartosz_s23375.models.Humans.Person;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Stock;
import jakarta.persistence.*;
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
public class StorageRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idStorageRoom;

    @NotNull(message = "Description of storage's location is required")
    @Size(min = 2, max = 2000, message = "Description has to be between 2 and 2000 characters long")
    private String location;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_music_store")
    @NotNull(message = "Music store owner class is required")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private MusicStore owner;

    @OneToMany(mappedBy = "storageRoom", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Stock> stocks = new HashSet<>();
}

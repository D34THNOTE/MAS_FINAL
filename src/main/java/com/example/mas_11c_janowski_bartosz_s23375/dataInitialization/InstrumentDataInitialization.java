package com.example.mas_11c_janowski_bartosz_s23375.dataInitialization;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.InstrumentImplementations.*;
import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.InstrumentStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class InstrumentDataInitialization {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createInstruments() {

        Guitar guitar1 = Guitar.builder()
                .modelName("Epiphone Songmaker DR-100")
                .price(60.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .numberOfStrings(6)
                .guitarType(GuitarType.ACOUSTIC)
                .heightInMillimeters(1000)
                .widthInMillimeters(60)
                .depthInMillimeters(50)
                .build();

        Guitar guitar2 = Guitar.builder()
                .modelName("Dowina Rioja DS")
                .price(500.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .numberOfStrings(6)
                .guitarType(GuitarType.ACOUSTIC)
                .heightInMillimeters(1000)
                .widthInMillimeters(60)
                .depthInMillimeters(50)
                .build();

        Guitar guitar3 = Guitar.builder()
                .modelName("De Salvo AG1NT")
                .price(600.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .numberOfStrings(6)
                .guitarType(GuitarType.ACOUSTIC)
                .heightInMillimeters(1000)
                .widthInMillimeters(60)
                .depthInMillimeters(50)
                .build();

        Drum drum1 = Drum.builder()
                .modelName("Epic tone deep")
                .price(300.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .drumSizeInInches(12.7)
                .heightInMillimeters(600)
                .drumType(DrumType.BASS)
                .build();

        Drum drum2 = Drum.builder()
                .modelName("Cool snare")
                .price(50.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .drumSizeInInches(12.7)
                .heightInMillimeters(400)
                .drumType(DrumType.SNARE)
                .build();

        Cymbal cymbal1 = Cymbal.builder()
                .modelName("Paiste Talerz HiHat 101 Brass")
                .price(70.0)
                .instrumentStatus(InstrumentStatus.INSTOCK)
                .cymbalSizeInInches(13.0)
                .cymbalType(CymbalType.HIHAT)
                .build();

        entityManager.persist(guitar1);
        entityManager.persist(guitar2);
        entityManager.persist(guitar3);

        entityManager.persist(drum1);
        entityManager.persist(drum2);

        entityManager.persist(cymbal1);
    }
}

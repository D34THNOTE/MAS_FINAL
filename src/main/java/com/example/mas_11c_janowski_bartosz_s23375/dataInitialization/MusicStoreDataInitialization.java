package com.example.mas_11c_janowski_bartosz_s23375.dataInitialization;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StoreAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MusicStoreDataInitialization {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createMusicStores() {
        MusicStore musicStore1 = MusicStore.builder()
                .storeName("On A High Note")
                .floorsNumber(2)
                .storeAddress(StoreAddress.builder()
                        .street("ul. Ordynacka")
                        .streetNumber("10/12")
                        .city("Warszawa")
                        .zipCode("00-358")
                        .country("Polska")
                        .build())
                .build();

        MusicStore musicStore2 = MusicStore.builder()
                .storeName("For The Record")
                .floorsNumber(1)
                .storeAddress(StoreAddress.builder()
                        .street("al. Armii Krajowej")
                        .streetNumber("5")
                        .city("Warszawa")
                        .zipCode("00-541")
                        .country("Polska")
                        .build())
                .build();

        // saving these objects to the database without a need of a method inside Repository
        entityManager.persist(musicStore1);
        entityManager.persist(musicStore2);
    }
}

package com.example.mas_11c_janowski_bartosz_s23375.dataInitialization;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MusicStoreDataInitialization musicStoreDataInitialization;
    private final InstrumentDataInitialization instrumentDataInitialization;

    @Override
    public void run(String... args) {
        musicStoreDataInitialization.createMusicStores();
        instrumentDataInitialization.createInstruments();
    }
}

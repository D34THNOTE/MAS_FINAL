package com.example.mas_11c_janowski_bartosz_s23375.services;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.InstrumentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public List<Instrument> listOfInstruments() {
        return (List<Instrument>) instrumentRepository.findAll();
    }

    public boolean verifyInstrumentExists(Instrument instrument) {
        return instrumentRepository.existsByModelName(instrument.getModelName());
    }

    public void saveInstrument(Instrument instrument) {
        // XOR implementation
        if(instrument == null) throw new IllegalArgumentException("Passed instrument is null");

        if(instrument.getMetal() != null && instrument.getWood() != null || instrument.getMetal() == null && instrument.getWood() == null) {
            throw new IllegalArgumentException("Instrument must have only one material type associated with it");
        }

        instrumentRepository.save(instrument);
    }
}

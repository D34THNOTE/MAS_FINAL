package com.example.mas_11c_janowski_bartosz_s23375.repositories;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstrumentRepository extends CrudRepository<Instrument, Long> {

    boolean existsByModelName(String modelName);

}

package com.example.mas_11c_janowski_bartosz_s23375.services;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.MusicStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicStoreService {

    private final MusicStoreRepository musicStoreRepository;

    public List<MusicStore> listOfMusicStores() {
        return (List<MusicStore>) musicStoreRepository.findAll();
    }

}

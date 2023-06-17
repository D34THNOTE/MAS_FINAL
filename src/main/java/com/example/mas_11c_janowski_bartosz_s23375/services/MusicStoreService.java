package com.example.mas_11c_janowski_bartosz_s23375.services;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.MusicStoreRepository;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.StorageRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicStoreService {

    private final MusicStoreRepository musicStoreRepository;

    private final StorageRoomRepository storageRoomRepository;

    public List<MusicStore> listOfMusicStores() {
        return (List<MusicStore>) musicStoreRepository.findAll();
    }

    public Optional<MusicStore> musicStoreDetails(Long idStore) {
        return (Optional<MusicStore>) musicStoreRepository.findById(idStore);
    }

    public List<StorageRoom> musicStoreAssociatedStorages(MusicStore musicStore) {
        return (List<StorageRoom>) storageRoomRepository.findAllByOwner(musicStore);
    }
}

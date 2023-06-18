package com.example.mas_11c_janowski_bartosz_s23375.services;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.StorageRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StorageService {

    private final StorageRoomRepository storageRoomRepository;

    public Optional<StorageRoom> fetchStorageRoom(Long idStorageRoom) {
        return storageRoomRepository.findById(idStorageRoom);
    }

}

package com.example.mas_11c_janowski_bartosz_s23375.repositories;

import com.example.mas_11c_janowski_bartosz_s23375.models.Store.MusicStore;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StorageRoomRepository extends CrudRepository<StorageRoom, Long> {

    public List<StorageRoom> findAllByOwner(MusicStore musicStore);

}

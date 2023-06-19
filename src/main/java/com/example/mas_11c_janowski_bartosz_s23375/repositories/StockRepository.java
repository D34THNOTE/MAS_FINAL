package com.example.mas_11c_janowski_bartosz_s23375.repositories;

import com.example.mas_11c_janowski_bartosz_s23375.models.Instruments.Instrument;
import com.example.mas_11c_janowski_bartosz_s23375.models.Store.StorageRoom;
import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Stock;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StockRepository extends CrudRepository<Stock, Long> {

    Optional<Stock> findStockByStorageRoomAndInstrument(StorageRoom storageRoom, Instrument instrument);

}

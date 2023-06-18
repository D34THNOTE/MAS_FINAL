package com.example.mas_11c_janowski_bartosz_s23375.services;

import com.example.mas_11c_janowski_bartosz_s23375.models.withAttribute.Stock;
import com.example.mas_11c_janowski_bartosz_s23375.repositories.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public boolean doesRecordExist(Stock stock) {
        return stockRepository.existsByStorageRoomAndInstrument(stock.getStorageRoom(), stock.getInstrument());
    }

    public boolean updateStock(Stock stock) {
        Stock downloadedStock = fetchExistingStockForUpdate(stock);

        int updatedValue = downloadedStock.getStockNumber() + stock.getStockNumber();
        downloadedStock.setStockNumber((double) updatedValue);

        if(downloadedStock.getIdStock() != null && downloadedStock.getStockNumber() != null && downloadedStock.getStorageRoom() != null
                && downloadedStock.getInstrument() != null) {
            stockRepository.save(downloadedStock);
            return true;
        }

        return false;
    }

    public boolean insertNewStock(Stock stock) {
        if(!doesRecordExist(stock) && stock.getStockNumber() != null && stock.getStorageRoom() != null && stock.getInstrument() != null) {
            stockRepository.save(stock);
            return true;
        }

        return false;
    }

    private Stock fetchExistingStockForUpdate(Stock stock) {
        return stockRepository.findStockByStorageRoomAndInstrument(stock.getStorageRoom(), stock.getInstrument());
    }

}

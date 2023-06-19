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

    public boolean updateStock(Stock stock) {
        Optional<Stock> downloadedStock = fetchStockForUpdate(stock);

        if(downloadedStock.isEmpty()) {
            return insertNewStock(stock);
        } else {
            int updatedValue = downloadedStock.get().getStockNumber() + stock.getStockNumber();
            downloadedStock.get().setStockNumber(updatedValue);

            return updateExistingStock(downloadedStock.get());
        }
    }

    private boolean insertNewStock(Stock stock) {
        if(stock.getStockNumber() != null && stock.getStorageRoom() != null && stock.getInstrument() != null) {
            stockRepository.save(stock);
            return true;
        }

        return false;
    }

    private boolean updateExistingStock(Stock stock) {
        if(stock.getStockNumber() != null && stock.getStorageRoom() != null && stock.getInstrument() != null) {
            stockRepository.save(stock);
            return true;
        }

        return false;
    }

    private Optional<Stock> fetchStockForUpdate(Stock stock) {
        return stockRepository.findStockByStorageRoomAndInstrument(stock.getStorageRoom(), stock.getInstrument());
    }

}

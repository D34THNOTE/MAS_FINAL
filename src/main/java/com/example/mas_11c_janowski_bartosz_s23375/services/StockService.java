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

    /**
     * Updates the stock based on the provided stock object. If the stock doesn't exist, a new stock is created.
     *
     * @param stock The stock object to update or insert.
     * @return True if the stock was successfully updated or inserted, false otherwise.
     */
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

    /**
     * Inserts a new stock into the database if the stock's stock number, storage room, and instrument are not null.
     *
     * @param stock The stock object to insert.
     * @return True if the stock was successfully inserted, false otherwise.
     */
    private boolean insertNewStock(Stock stock) {
        if(stock.getStockNumber() != null && stock.getStorageRoom() != null && stock.getInstrument() != null) {
            stockRepository.save(stock);
            return true;
        }

        return false;
    }

    /**
     * Updates an existing stock in the database if the stock's stock number, storage room, and instrument are not null.
     *
     * @param stock The stock object to update.
     * @return True if the stock was successfully updated, false otherwise.
     */
    private boolean updateExistingStock(Stock stock) {
        if(stock.getStockNumber() != null && stock.getStorageRoom() != null && stock.getInstrument() != null) {
            stockRepository.save(stock);
            return true;
        }

        return false;
    }

    /**
     * Fetches the stock object for update based on the storage room and instrument.
     *
     * @param stock The stock object containing the storage room and instrument information.
     * @return Optional containing the stock object if found, or empty if not found.
     */
    private Optional<Stock> fetchStockForUpdate(Stock stock) {
        return stockRepository.findStockByStorageRoomAndInstrument(stock.getStorageRoom(), stock.getInstrument());
    }

}

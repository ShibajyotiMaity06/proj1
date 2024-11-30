package com.programmer.Store.Service.service.impl;


import com.programmer.Store.Service.model.Stock;
import com.programmer.Store.Service.model.StockActionType;
import com.programmer.Store.Service.model.StockHistory;
import com.programmer.Store.Service.repository.StockHistoryRepository;
import com.programmer.Store.Service.service.StockHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockHistoryServiceImpl implements StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;

    public StockHistoryServiceImpl(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }

    @Override
    public List<StockHistory> getAllStockHistories() {
        return stockHistoryRepository.findAll();
    }

    @Override
    public List<StockHistory> getHistoryOfProductInStore(String storeCode, String productCode) {
        return stockHistoryRepository.findByStoreCodeAndProductCode(storeCode, productCode);
    }

    @Override
    public List<StockHistory> getHistoryOfProduct(String productCode) {
        return stockHistoryRepository.findByProductCode(productCode);
    }

    @Override
    public List<StockHistory> getHistoryOfStore(String storeCode) {
        return stockHistoryRepository.findByStoreCode(storeCode);
    }


    @Override
    public void createStockHistory(Stock stock, int previousQuantity, int newQuantity, StockActionType actionType) {
        StockHistory stockHistory = new StockHistory();
        stockHistory.setStore(stock.getStore());
        stockHistory.setProductCode(stock.getProductCode());
        stockHistory.setActionDate(LocalDateTime.now());
        stockHistory.setAction(actionType);
        stockHistory.setPreviousQuantity(previousQuantity);
        stockHistory.setNewQuantity(newQuantity);
        stockHistoryRepository.save(stockHistory);
    }
}

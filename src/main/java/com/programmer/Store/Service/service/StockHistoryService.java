package com.programmer.Store.Service.service;


import com.programmer.Store.Service.model.Stock;
import com.programmer.Store.Service.model.StockActionType;
import com.programmer.Store.Service.model.StockHistory;

import java.util.List;

public interface StockHistoryService {
    List<StockHistory> getAllStockHistories();

    List<StockHistory> getHistoryOfProductInStore(String storeCode, String productCode);

    List<StockHistory> getHistoryOfProduct(String productCode);

    List<StockHistory> getHistoryOfStore(String storeCode);

    void createStockHistory(Stock stock, int previousQuantity, int newQuantity, StockActionType actionType);
}

package com.programmer.Store.Service.service;

import com.programmer.Store.Service.model.AllocatedStoreInfo;
import com.programmer.Store.Service.model.ProductRequest;
import com.programmer.Store.Service.model.Stock;
import com.programmer.Store.Service.model.StockRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface StockService {
    List<Stock> getAllStocks();

    Stock getStockByCode(String code);

    List<Stock> getStocksByStoreCode(String storeCode);

    Stock getStockByStoreCodeAndProductCode(String storeCode, String productCode);

    int getQuantity(String productCode);

    @Transactional
    List<AllocatedStoreInfo> makeOrder(List<ProductRequest> productRequests);

    @Transactional
    void increaseStockQuantity(StockRequest stockRequest);

    @Transactional
    void decreaseStockQuantity(StockRequest stockRequest);

    int getQuantityInStore(String storeCode, String productCode);
}

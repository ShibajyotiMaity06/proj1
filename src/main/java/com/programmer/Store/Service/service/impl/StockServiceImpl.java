package com.programmer.Store.Service.service.impl;

import com.programmer.Store.Service.exception.InsufficientStockException;
import com.programmer.Store.Service.exception.StockNotFoundException;
import com.programmer.Store.Service.model.*;
import com.programmer.Store.Service.repository.StockRepository;
import com.programmer.Store.Service.service.StockService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final StockHistoryServiceImpl stockHistoryServiceImpl;
    private final StoreServiceImpl storeServiceImpl;

    public StockServiceImpl(StockRepository stockRepository, StockHistoryServiceImpl stockHistoryServiceImpl, StoreServiceImpl storeServiceImpl) {
        this.stockRepository = stockRepository;
        this.stockHistoryServiceImpl = stockHistoryServiceImpl;
        this.storeServiceImpl = storeServiceImpl;
    }


    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getStockByCode(String code) {
        return stockRepository.findById(code)
                .orElseThrow(() -> new StockNotFoundException("Stock with Code " + code + " not found"));
    }

    @Override
    public List<Stock> getStocksByStoreCode(String storeCode) {
        return stockRepository.findByStore_Code(storeCode);
    }

    @Override
    public Stock getStockByStoreCodeAndProductCode(String storeCode, String productCode) {
        return stockRepository.findByStore_CodeAndProductCode(storeCode, productCode)
                .stream()
                .findAny()
                .orElseThrow(() -> new StockNotFoundException("Stock with Store Code " + storeCode + " and Product Code " + productCode + " not found"));
    }

    @Override
    public int getQuantity(String productCode) {
        List<Stock> stocks = stockRepository.findByProductCode(productCode);

        int totalAvailableQuantity = stocks.stream()
                .mapToInt(Stock::getQuantity)
                .sum();

        return totalAvailableQuantity;
    }

    @Override
    public int getQuantityInStore(String storeCode, String productCode) {
        return stockRepository.findByStore_CodeAndProductCode(storeCode, productCode)
                .stream()
                .findFirst()
                .map(Stock::getQuantity)
                .orElse(0);
    }

    @Override
    @Transactional
    public List<AllocatedStoreInfo> makeOrder(List<ProductRequest> productRequests) {

        HashMap<String, AllocatedStoreInfo> allocatedStoresMap = new HashMap<>();
        for (var productRequest : productRequests) {

            String productCode = productRequest.getProductCode();
            int remainingQuantity = productRequest.getQuantity();

            List<Stock> stocks = stockRepository.findByProductCode(productCode);

            for (var stock : stocks) {
                int availableQuantity = stock.getQuantity();
                int allocateQuantity = Math.min(remainingQuantity, availableQuantity);
                String storeCode = stock.getStore().getCode();
                String location = storeServiceImpl.getStoreByCode(storeCode).getLocation();

                var allocatedStockInfo = new AllocatedStockInfo(productCode, allocateQuantity);

                if (allocateQuantity > 0) {
                    decreaseStockQuantity(new StockRequest(storeCode, stock.getProductCode(), allocateQuantity));

                    if (allocatedStoresMap.containsKey(storeCode)) {
                        var allocatedStoreInfo = allocatedStoresMap.get(storeCode);
                        allocatedStoreInfo.getAllocatedStockInfos().add(allocatedStockInfo);
                    } else {
                        var allocatedStoreInfo = new AllocatedStoreInfo(storeCode, location);
                        allocatedStoreInfo.getAllocatedStockInfos().add(allocatedStockInfo);
                        allocatedStoresMap.put(storeCode, allocatedStoreInfo);
                    }

                    remainingQuantity -= allocateQuantity;
                }


                if (remainingQuantity == 0) {
                    break;
                }
            }

            if (remainingQuantity != 0) {
                throw new InsufficientStockException("Insufficient stock for product: " + productCode);
            }

        }

        return allocatedStoresMap
                .values()
                .stream()
                .toList();
    }


    @Override
    @Transactional
    public void increaseStockQuantity(StockRequest stockRequest) {
        Store store = storeServiceImpl.getStoreByCode(stockRequest.getStoreCode());
        Stock stock = stockRepository.findByStoreAndProductCode(store, stockRequest.getProductCode())
                .stream()
                .findAny()
                .orElse(new Stock(store, stockRequest.getProductCode(), 0));

        int previousQuantity = stock.getQuantity();
        int newQuantity = previousQuantity + stockRequest.getQuantity();
        stock.setQuantity(newQuantity);
        stockRepository.save(stock);

        // Create Stock History
        stockHistoryServiceImpl.createStockHistory(stock, previousQuantity, newQuantity, StockActionType.INCREASE);
    }

    @Override
    @Transactional
    public void decreaseStockQuantity(StockRequest stockRequest) {
        Store store = storeServiceImpl.getStoreByCode(stockRequest.getStoreCode());
        Stock stock = stockRepository.findByStoreAndProductCode(store, stockRequest.getProductCode())
                .stream()
                .findAny()
                .orElse(new Stock(store, stockRequest.getProductCode(), 0));

        int previousQuantity = stock.getQuantity();
        int newQuantity = previousQuantity - stockRequest.getQuantity();
        if (newQuantity < 0) {
            throw new InsufficientStockException("Insufficient stock to decrease. Available stock: " + stock.getQuantity());
        }
        stock.setQuantity(newQuantity);
        stockRepository.save(stock);

        // Create Stock History
        stockHistoryServiceImpl.createStockHistory(stock, previousQuantity, newQuantity, StockActionType.DECREASE);
    }

}

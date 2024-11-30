package com.programmer.Store.Service.service.impl;

import com.programmer.Store.Service.exception.StoreNotFoundException;
import com.programmer.Store.Service.model.Store;
import com.programmer.Store.Service.repository.StockHistoryRepository;
import com.programmer.Store.Service.repository.StockRepository;
import com.programmer.Store.Service.repository.StoreRepository;
import com.programmer.Store.Service.service.StoreService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository, StockRepository stockRepository, StockHistoryRepository stockHistoryRepository) {
        this.storeRepository = storeRepository;
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreByCode(String code) {
        return storeRepository.findById(code)
                .orElseThrow(() -> new StoreNotFoundException("Store with Code " + code + " not found"));
    }

    @Override
    public Store createNewStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Store updateStore(Store store) {
        if (!storeRepository.existsById(store.getCode())) {
            throw new StoreNotFoundException("Store with Code " + store.getCode() + " not found");
        }
        return storeRepository.save(store);
    }

    @Override
    @Transactional
    public void deleteStore(String code) {
        if (!storeRepository.existsById(code)) {
            throw new StoreNotFoundException("Store with Code " + code + " not found");
        }

        stockRepository.deleteAllByStore_Code(code);
        stockHistoryRepository.deleteAllByStore_Code(code);
        storeRepository.deleteById(code);
    }
}

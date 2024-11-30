package com.programmer.Store.Service.repository;

import com.programmer.Store.Service.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, String> {
    List<StockHistory> findByStoreCode(String storeCode);
    List<StockHistory> findByProductCode(String productCode);
    List<StockHistory> findByStoreCodeAndProductCode(String storeCode, String productCode);
    void deleteAllByStore_Code(String storeCode);
}

package com.programmer.Store.Service.repository;

import com.programmer.Store.Service.model.Stock;
import com.programmer.Store.Service.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    List<Stock> findByStore_Code(String storeCode);
    List<Stock> findByStore(Store store);

    List<Stock> findByProductCode(String productCode);

    List<Stock> findByStore_CodeAndProductCode(String StoreCode, String productCode);
    List<Stock> findByStoreAndProductCode(Store store, String productCode);

    void deleteAllByStore_Code(String storeCode);

    boolean existsByStore_CodeAndProductCode(String storeCode, String productCode);
}
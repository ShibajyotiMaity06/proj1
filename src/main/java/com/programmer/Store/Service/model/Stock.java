package com.programmer.Store.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"store_code", "product_code"})})
public class Stock implements Serializable {
    @Id
    private String code;

    @ManyToOne
    @JoinColumn(name = "store_code")
    private Store store;


    @Column(name = "product_code")
    @JsonProperty("product_code")
    private String productCode;

    private int quantity;

    public Stock (Store store, String productCode, int quantity){
        this.store = store;
        this.productCode = productCode;
        this.quantity = quantity;
    }


    @PrePersist
    public void generateStockCode() {
        this.code = generateCode("STK");
    }

    private String generateCode(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 6);
    }

    public StockRequest toStockRequest() {
        StockRequest stockRequest = new StockRequest();
        stockRequest.setStoreCode(this.store.getCode());
        stockRequest.setProductCode(this.getProductCode());
        stockRequest.setQuantity(this.getQuantity());
        return stockRequest;
    }
}

package com.programmer.Store.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockRequest {
    @JsonProperty("store_code")
    private String storeCode;
    @JsonProperty("product_code")
    private String productCode;
    private int quantity;

    public boolean valid() {
        return storeCode != null && productCode != null;
    }
}

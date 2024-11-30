package com.programmer.Store.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllocatedStockInfo {
    @JsonProperty("product_code")
    private String productCode;
    private int quantity;
}

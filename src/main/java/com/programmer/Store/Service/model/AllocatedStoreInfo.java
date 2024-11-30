package com.programmer.Store.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllocatedStoreInfo {
    @JsonProperty("store_code")
    private String storeCode;
    private String location;

    @JsonProperty("allocated_stock_infos")
    private List<AllocatedStockInfo> allocatedStockInfos = new ArrayList<>();

    public AllocatedStoreInfo(String storeCode, String location) {
        this.storeCode = storeCode;
        this.location = location;
    }
}

package com.programmer.Store.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class StockHistory implements Serializable {
    @Id
    private String code;

    @ManyToOne
    @JoinColumn(name = "store_code")
    private Store store;

    @JsonProperty("product_code")
    private String productCode;

    @Enumerated(EnumType.STRING)
    private StockActionType action; // Enum representing actions like "INCREASE", "DECREASE", ...;

    @JsonProperty("action_date")
    private LocalDateTime actionDate;

    @JsonProperty("previous_quantity")
    private int previousQuantity;

    @JsonProperty("new_quantity")
    private int newQuantity;

    @PrePersist
    public void generateHistoryCode() {
        this.code = generateCode("HIS");
    }

    private String generateCode(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 6);
    }
}

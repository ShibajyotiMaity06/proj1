package com.programmer.Store.Service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Store implements Serializable {
    @Id
    @Column(nullable = false)
    private String code;

    private String name;
    private String location;

    @JsonProperty("phone_number")
    private String phoneNumber;

    public Store(@NonNull String name, @NonNull String location, @NonNull String phoneNumber) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    @PrePersist
    public void generateStoreCode() {
        this.code = generateCode("STR");
    }

    private String generateCode(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 6);
    }

}

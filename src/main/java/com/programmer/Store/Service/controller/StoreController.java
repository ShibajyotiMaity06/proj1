package com.programmer.Store.Service.controller;

import com.programmer.Store.Service.model.Store;
import com.programmer.Store.Service.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    public final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping({ "/", "" })
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    @GetMapping({ "/{code}" })
    public ResponseEntity<Store> getStoreById(@PathVariable String code) {
        Store store = storeService.getStoreByCode(code);
        return new ResponseEntity<>(store, HttpStatus.OK);
    }

    @PostMapping({ "/", "" })
    public ResponseEntity<Store> createNewStore(@RequestBody Store store) {
        Store newStore = storeService.createNewStore(store);
        return new ResponseEntity<>(newStore, HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Store> updateStore(@PathVariable String code, @RequestBody Store store) {
        store.setCode(code);
        Store updatedStore = storeService.updateStore(store);
        return new ResponseEntity<>(updatedStore, HttpStatus.OK);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteStore(@PathVariable String code) {
        storeService.deleteStore(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

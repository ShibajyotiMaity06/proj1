package com.programmer.Store.Service.controller;

import com.programmer.Store.Service.model.AllocatedStoreInfo;
import com.programmer.Store.Service.model.ProductRequest;
import com.programmer.Store.Service.model.Stock;
import com.programmer.Store.Service.model.StockRequest;
import com.programmer.Store.Service.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping({ "", "/" })
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public Stock getStockByCode(@PathVariable String code) {
        return stockService.getStockByCode(code);
    }

    @GetMapping("/store/{code}")
    public ResponseEntity<List<Stock>> getStocksByStoreCode(@PathVariable String code) {
        var stocks = stockService.getStocksByStoreCode(code);
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/store/{storeCode}/product/{productCode}")
    public ResponseEntity<Stock> getStockByStoreAndProduct(
            @PathVariable String storeCode,
            @PathVariable String productCode) {
        var stock = stockService.getStockByStoreCodeAndProductCode(storeCode, productCode);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping("/store/{storeCode}/product/{productCode}/quantity")
    public ResponseEntity<Integer> getQuantityInStore(@PathVariable String storeCode, @PathVariable String productCode) {
        int quantity = stockService.getQuantityInStore(storeCode, productCode);
        return new ResponseEntity<>(quantity, HttpStatus.OK);
    }

    @PostMapping("/increase")
    public ResponseEntity<Map<String, String>> increaseStock(@RequestBody StockRequest stockRequest) {
        Map<String, String> response = new HashMap<>();
        if (!stockRequest.valid()) {
            response.put("error", "store_code and product_code should be Not Null.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        stockService.increaseStockQuantity(stockRequest);
        response.put("message", "Stock quantity increased successfully.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/decrease")
    public ResponseEntity<Map<String, String>> decreaseStock(@RequestBody StockRequest stockRequest) {
        Map<String, String> response = new HashMap<>();
        if (!stockRequest.valid()) {
            response.put("error", "store_code and product_code should be Not Null.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        stockService.decreaseStockQuantity(stockRequest);
        response.put("message", "Stock quantity decreased successfully.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{productCode}/quantity")
    public ResponseEntity<Integer> getQuantity(
            @PathVariable String productCode) {
        int quantity = stockService.getQuantity(productCode);
        return ResponseEntity.ok(quantity);
    }

    @PostMapping("/make_order")
    public ResponseEntity<List<AllocatedStoreInfo>> makeOrder(@RequestBody List<ProductRequest> productRequests) {
        List<AllocatedStoreInfo> allocatedStoreInfos = stockService.makeOrder(productRequests);
        return ResponseEntity.ok(allocatedStoreInfos);
    }

}

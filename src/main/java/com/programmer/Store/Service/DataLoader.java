package com.programmer.Store.Service;

import com.programmer.Store.Service.model.Stock;
import com.programmer.Store.Service.model.StockRequest;
import com.programmer.Store.Service.model.Store;
import com.programmer.Store.Service.service.StockService;
import com.programmer.Store.Service.service.StoreService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    private final StoreService storeService;
    private final StockService stockService;

    public DataLoader(StoreService storeService, StockService stockService) {
        this.storeService = storeService;
        this.stockService = stockService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        insertSampleData();
    }

    private void insertSampleData() {
        String product1 = "Pro1";
        String product2 = "Pro2";
        String product3 = "Pro3";

        Store storeA = new Store( "Store A", "Location A", "01006674956");
        storeA = storeService.createNewStore(storeA);

        Store storeB = new Store("Store B", "Location B", "01555822890");
        storeB = storeService.createNewStore(storeB);

        Store storeC = new Store( "Store C", "Location C", "01203722785");
        storeC = storeService.createNewStore(storeC);



        Stock stock1 = new Stock( storeA, product1, 10);
        Stock stock2 = new Stock( storeA, product2, 15);

        Stock stock3 = new Stock( storeB, product1, 4);
        Stock stock4 = new Stock( storeB, product2, 5);


        Stock stock5 = new Stock( storeC, product3, 44);
        Stock stock6 = new Stock( storeC, product2, 55);


        stockService.increaseStockQuantity(stock1.toStockRequest());
        stockService.increaseStockQuantity(stock2.toStockRequest());
        stockService.increaseStockQuantity(stock3.toStockRequest());
        stockService.increaseStockQuantity(stock4.toStockRequest());
        stockService.increaseStockQuantity(stock5.toStockRequest());
        stockService.increaseStockQuantity(stock6.toStockRequest());


        stockService.increaseStockQuantity(new StockRequest(storeA.getCode(), product1, 10));
        stockService.increaseStockQuantity(new StockRequest(storeA.getCode(), product1, 11));
        stockService.decreaseStockQuantity(new StockRequest(storeA.getCode(), product1, 4));

        stockService.increaseStockQuantity(new StockRequest(storeB.getCode(), product1, 20));
        stockService.increaseStockQuantity(new StockRequest(storeB.getCode(), product1, 21));
        stockService.increaseStockQuantity(new StockRequest(storeB.getCode(), product2, 30));
        stockService.increaseStockQuantity(new StockRequest(storeB.getCode(), product2, 31));

        stockService.decreaseStockQuantity(new StockRequest(storeB.getCode(), product1, 20));
        stockService.decreaseStockQuantity(new StockRequest(storeB.getCode(), product1, 21));
        stockService.decreaseStockQuantity(new StockRequest(storeB.getCode(), product2, 30));
        stockService.decreaseStockQuantity(new StockRequest(storeB.getCode(), product2, 31));

    }

}

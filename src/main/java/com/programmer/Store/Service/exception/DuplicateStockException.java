package com.programmer.Store.Service.exception;

public class DuplicateStockException extends RuntimeException {

    public DuplicateStockException(String message) {
        super(message);
    }
}
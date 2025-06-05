package com.utp.inventarios.exception;

public class NotEnoughStockException extends RuntimeException {

    // Constructor con mensaje personalizado
    public NotEnoughStockException(String message) {
        super(message);
    }

    // Constructor con mensaje y causa
    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
}

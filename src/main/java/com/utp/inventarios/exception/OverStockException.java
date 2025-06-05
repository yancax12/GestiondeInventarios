package com.utp.inventarios.exception;

public class OverStockException extends RuntimeException {

    public OverStockException(String message) {
        super(message);  // Llama al constructor de la clase padre con el mensaje
    }
}

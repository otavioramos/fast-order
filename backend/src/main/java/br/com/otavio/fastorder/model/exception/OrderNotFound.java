package br.com.otavio.fastorder.model.exception;

public class OrderNotFound extends RuntimeException {

    public OrderNotFound(String message) {
        super(message);
    }
}

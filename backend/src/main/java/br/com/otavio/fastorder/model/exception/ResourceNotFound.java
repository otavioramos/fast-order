package br.com.otavio.fastorder.model.exception;

public abstract class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String message) {
        super(message);
    }
}

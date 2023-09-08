package br.com.otavio.fastorder.controller.exception;

import br.com.otavio.fastorder.model.dto.ExceptionDTO;
import br.com.otavio.fastorder.model.exception.OrderNotFound;
import br.com.otavio.fastorder.model.exception.PreparationTimeException;
import br.com.otavio.fastorder.model.exception.ProductNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(OrderNotFound.class)
    public ResponseEntity<ExceptionDTO> orderNotFound(OrderNotFound exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }

    @ExceptionHandler(ProductNotFound.class)
    public ResponseEntity<ExceptionDTO> orderNotFound(ProductNotFound exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }

    @ExceptionHandler(PreparationTimeException.class)
    public ResponseEntity<ExceptionDTO> orderNotFound(PreparationTimeException exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> genericException(Exception exception) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(
                "Internal Server Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}

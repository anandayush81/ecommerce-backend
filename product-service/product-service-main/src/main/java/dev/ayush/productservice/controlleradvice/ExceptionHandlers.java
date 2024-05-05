package dev.ayush.productservice.controlleradvice;

import dev.ayush.productservice.dtos.ExceptionDto;
import dev.ayush.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleProductNotFoundException(ProductNotFoundException e) {
        return new ResponseEntity<>(
                new ExceptionDto(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}

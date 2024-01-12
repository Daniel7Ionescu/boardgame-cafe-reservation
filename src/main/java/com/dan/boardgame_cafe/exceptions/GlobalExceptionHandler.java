package com.dan.boardgame_cafe.exceptions;

import com.dan.boardgame_cafe.exceptions.customer.CustomerBelowMinimumAgeException;
import com.dan.boardgame_cafe.exceptions.customer.CustomerEmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, Object> result = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> result.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerBelowMinimumAgeException.class)
    public ResponseEntity<Object> handleCustomerBelowMinimumAgeException(CustomerBelowMinimumAgeException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerEmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleCustomerEmailAlreadyExistsException(CustomerEmailAlreadyExistsException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponse(RuntimeException e, HttpStatus httpStatus) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());
        return new ResponseEntity<>(result, httpStatus);
    }
}

package com.dan.boardgame_cafe.exceptions;

import com.dan.boardgame_cafe.exceptions.general.DuplicateResourceException;
import com.dan.boardgame_cafe.exceptions.general.ResourceHasInvalidPropertiesException;
import com.dan.boardgame_cafe.exceptions.general.ResourceNotFoundException;
import com.dan.boardgame_cafe.exceptions.general.ResourceInvalidUsageException;
import com.dan.boardgame_cafe.models.dtos.error.ErrorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, Object> result = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> result.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), NOT_FOUND);
        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    protected ResponseEntity<Object> handleDuplicateResourceException(DuplicateResourceException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), BAD_REQUEST);
        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(ResourceHasInvalidPropertiesException.class)
    protected ResponseEntity<Object> handleResourceHasInvalidPropertiesException(ResourceHasInvalidPropertiesException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), BAD_REQUEST);
        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(ResourceInvalidUsageException.class)
    protected ResponseEntity<Object> handleResourceInvalidUsageException(ResourceInvalidUsageException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), BAD_REQUEST);
        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        String result = String.valueOf(e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessageTemplate)
                .findFirst());
        ErrorDTO errorDTO = new ErrorDTO(result, BAD_REQUEST);

        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), BAD_REQUEST);
        return buildResponseEntity(errorDTO);
    }

    @ExceptionHandler(DateTimeParseException.class)
    protected ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), BAD_REQUEST);
        return buildResponseEntity(errorDTO);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorDTO errorDTO) {
        return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
    }
}

package com.dan.boardgame_cafe.exceptions;

import com.dan.boardgame_cafe.exceptions.game.DuplicateGameException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationInvalidAgeException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationMinimumDurationException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationOutsideWorkingHoursException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
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

    @ExceptionHandler(ReservationInvalidAgeException.class)
    public ResponseEntity<Object> handleReservationInvalidAgeException(ReservationInvalidAgeException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationOutsideWorkingHoursException.class)
    public ResponseEntity<Object> handleReservationOutsideWorkingHoursException(ReservationOutsideWorkingHoursException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationMinimumDurationException.class)
    public ResponseEntity<Object> handleReservationMinimumDurationException(ReservationMinimumDurationException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateGameException.class)
    public ResponseEntity<Object> handleDuplicateGameException(DuplicateGameException e) {
        return getResponse(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getResponse(RuntimeException e, HttpStatus httpStatus) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());
        return new ResponseEntity<>(result, httpStatus);
    }
}

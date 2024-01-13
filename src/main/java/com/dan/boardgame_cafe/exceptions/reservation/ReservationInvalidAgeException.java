package com.dan.boardgame_cafe.exceptions.reservation;

public class ReservationInvalidAgeException extends RuntimeException {

    public ReservationInvalidAgeException(String message) {
        super(message);
    }
}

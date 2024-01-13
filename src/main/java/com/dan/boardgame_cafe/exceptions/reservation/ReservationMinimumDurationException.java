package com.dan.boardgame_cafe.exceptions.reservation;

public class ReservationMinimumDurationException extends RuntimeException {

    public ReservationMinimumDurationException(String message) {
        super(message);
    }
}

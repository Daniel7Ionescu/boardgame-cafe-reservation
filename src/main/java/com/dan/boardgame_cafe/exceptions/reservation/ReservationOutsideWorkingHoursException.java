package com.dan.boardgame_cafe.exceptions.reservation;

public class ReservationOutsideWorkingHoursException extends RuntimeException {

    public ReservationOutsideWorkingHoursException(String message) {
        super(message);
    }
}

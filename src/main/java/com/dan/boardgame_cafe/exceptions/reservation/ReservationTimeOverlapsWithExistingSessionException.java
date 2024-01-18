package com.dan.boardgame_cafe.exceptions.reservation;

public class ReservationTimeOverlapsWithExistingSessionException extends RuntimeException{

    public ReservationTimeOverlapsWithExistingSessionException(String message){super(message);}
}

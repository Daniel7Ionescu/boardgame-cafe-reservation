package com.dan.boardgame_cafe.exceptions.reservation;

public class ResultingSessionOverlapsWithExistingException extends RuntimeException{

    public ResultingSessionOverlapsWithExistingException(String message){super(message);}
}

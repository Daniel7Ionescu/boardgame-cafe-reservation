package com.dan.boardgame_cafe.exceptions.customer;

public class CustomerBelowMinimumAgeException extends RuntimeException{
    public CustomerBelowMinimumAgeException(String message){super(message);}
}

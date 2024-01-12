package com.dan.boardgame_cafe.exceptions.customer;

public class CustomerEmailAlreadyExistsException extends RuntimeException{
    public CustomerEmailAlreadyExistsException(String message){super(message);}
}

package com.dan.boardgame_cafe.exceptions.general;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}

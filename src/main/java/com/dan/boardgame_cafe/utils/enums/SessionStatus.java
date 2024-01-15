package com.dan.boardgame_cafe.utils.enums;

public enum SessionStatus {

    READY("Ready"),
    FAILED("Failed"),
    SUCCESS("Success");

    private final String sessionStatusLabel;

    SessionStatus(String sessionStatusLabel) {
        this.sessionStatusLabel = sessionStatusLabel;
    }
}

package com.dan.boardgame_cafe.utils.enums;

public enum SessionType {

    STANDARD("Standard"),
    SPECIAL_EVENT("Special Event");

    private final String sessionTypeLabel;

    SessionType(String sessionTypeLabel) {
        this.sessionTypeLabel = sessionTypeLabel;
    }
}

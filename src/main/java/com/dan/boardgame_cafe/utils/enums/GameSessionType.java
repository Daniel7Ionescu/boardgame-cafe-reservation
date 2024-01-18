package com.dan.boardgame_cafe.utils.enums;

import lombok.Getter;

@Getter
public enum GameSessionType {

    STANDARD("Standard"),
    EVENT("Event");

    private final String gameSessionTypeLabel;

    GameSessionType(String gameSessionTypeLabel) {
        this.gameSessionTypeLabel = gameSessionTypeLabel;
    }
}

package com.dan.boardgame_cafe.utils.enums;

import lombok.Getter;

@Getter
public enum GameSessionStatus {

    ACTIVE("Active"),
    ENDED("Ended");


    private final String sessionStatusLabel;

    GameSessionStatus(String sessionStatusLabel) {
        this.sessionStatusLabel = sessionStatusLabel;
    }
}

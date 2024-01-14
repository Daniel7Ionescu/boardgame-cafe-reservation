package com.dan.boardgame_cafe.utils.enums;

import lombok.Getter;

@Getter
public enum GameCategory {

    RPG("RPG"),
    SOCIAL("Social"),
    CARD_GAME("Card Game");

    private final String gameCategoryLabel;

    GameCategory(String gameCategoryLabel) {
        this.gameCategoryLabel = gameCategoryLabel;
    }
}

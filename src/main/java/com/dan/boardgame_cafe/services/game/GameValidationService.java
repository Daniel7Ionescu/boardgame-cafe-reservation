package com.dan.boardgame_cafe.services.game;

import com.dan.boardgame_cafe.models.dtos.game.GameDTO;

public interface GameValidationService {

    void validateUniqueGameName(GameDTO gameDTO);
}

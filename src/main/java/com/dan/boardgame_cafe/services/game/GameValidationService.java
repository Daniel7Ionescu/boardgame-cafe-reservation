package com.dan.boardgame_cafe.services.game;

import com.dan.boardgame_cafe.models.dtos.game.GameDTO;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.models.entities.GameSession;

public interface GameValidationService {

    void validateUniqueGameName(GameDTO gameDTO);
}

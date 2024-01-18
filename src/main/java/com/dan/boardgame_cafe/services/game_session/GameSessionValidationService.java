package com.dan.boardgame_cafe.services.game_session;

import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.models.entities.GameSession;

public interface GameSessionValidationService {

    void validateGameSessionCanAddGame(GameSession gameSession, Game game);
}

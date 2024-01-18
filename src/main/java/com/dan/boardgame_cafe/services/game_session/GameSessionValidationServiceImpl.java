package com.dan.boardgame_cafe.services.game_session;

import com.dan.boardgame_cafe.exceptions.game.GameAlreadyInGameSessionException;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.models.entities.GameSession;
import org.springframework.stereotype.Service;

@Service
public class GameSessionValidationServiceImpl implements GameSessionValidationService{
    @Override
    public void validateGameSessionCanAddGame(GameSession gameSession, Game game) {
        if(gameSession.getGames().contains(game)){
            throw new GameAlreadyInGameSessionException("Game id: " + game.getId() + " already in game session");
        }
    }
}

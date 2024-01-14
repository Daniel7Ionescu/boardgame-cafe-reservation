package com.dan.boardgame_cafe.services.game;

import com.dan.boardgame_cafe.models.dtos.game.GameDTO;

import java.util.List;

public interface GameService {

    GameDTO createGame(GameDTO gameDTO);
    List<GameDTO> getAllGames();
}

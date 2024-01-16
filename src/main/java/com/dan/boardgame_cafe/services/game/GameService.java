package com.dan.boardgame_cafe.services.game;

import com.dan.boardgame_cafe.models.dtos.game.GameDTO;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.utils.enums.GameCategory;

import java.util.List;

public interface GameService {

    GameDTO createGame(GameDTO gameDTO);
    List<GameDTO> getAllGames(String inputName, GameCategory gameCategory, Integer minPlayers);
    Game retrieveGameById(Long id);
}

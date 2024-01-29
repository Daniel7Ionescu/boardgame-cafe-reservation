package com.dan.boardgame_cafe.services.game;

import com.dan.boardgame_cafe.models.dtos.game.GameDTO;
import com.dan.boardgame_cafe.models.dtos.game.GameDetailDTO;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.utils.enums.GameCategory;

import java.util.List;

public interface GameService {

    GameDetailDTO createGame(GameDTO gameDTO);
    List<GameDetailDTO> getFilteredGames(String inputName, GameCategory gameCategory, Integer minPlayers);
    GameDetailDTO getGameById(Long gameId);
    void deleteGame(Long gameId);
    Game retrieveGameById(Long gameId);
}

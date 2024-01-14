package com.dan.boardgame_cafe.services.game;

import com.dan.boardgame_cafe.exceptions.game.DuplicateGameException;
import com.dan.boardgame_cafe.models.dtos.game.GameDTO;
import com.dan.boardgame_cafe.repositories.GameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameValidationServiceImpl implements GameValidationService{

    private final GameRepository gameRepository;

    public GameValidationServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void validateUniqueGameName(GameDTO gameDTO) {
        long result = gameRepository.countByGameName(gameDTO.getGameName());
        System.out.println("Count: " + result);
        if(result > 0){
            throw new DuplicateGameException("A game with that name already exists");
        }
    }
}

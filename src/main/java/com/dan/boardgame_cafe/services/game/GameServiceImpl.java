package com.dan.boardgame_cafe.services.game;

import com.dan.boardgame_cafe.exceptions.ResourceNotFoundException;
import com.dan.boardgame_cafe.models.dtos.game.GameDTO;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.repositories.GameRepository;
import com.dan.boardgame_cafe.utils.enums.GameCategory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dan.boardgame_cafe.utils.specifications.GameSpecification.*;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    public final GameValidationService gameValidationService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, GameValidationService gameValidationService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.gameValidationService = gameValidationService;
    }

    @Override
    public GameDTO createGame(GameDTO gameDTO) {
        gameValidationService.validateUniqueGameName(gameDTO);

        Game game = modelMapper.map(gameDTO, Game.class);
        Game savedGame = gameRepository.save(game);
        log.info("Game {} with id: {} saved in database.", savedGame.getGameName(), savedGame.getId());

        return modelMapper.map(savedGame, GameDTO.class);
    }

    @Override
    public List<GameDTO> getAllGames(String inputName, GameCategory gameCategory, Integer minPlayers) {
        Specification<Game> gameFilter = Specification
                .where(inputName == null ? null : gameNameLike(inputName))
                .and(gameCategory == null ? null : hasGameCategory(gameCategory))
                .and(minPlayers == null ? null : gamePlayers(minPlayers));

        return gameRepository.findAll(gameFilter).stream()
                .map(game -> modelMapper.map(game, GameDTO.class))
                .toList();
    }

    @Override
    public Game retrieveGameById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game with id: " + id + " not found"));
    }
}
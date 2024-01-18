package com.dan.boardgame_cafe.unit_tests;

import com.dan.boardgame_cafe.models.dtos.game.GameDTO;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.repositories.GameRepository;
import com.dan.boardgame_cafe.services.game.GameServiceImpl;
import com.dan.boardgame_cafe.services.game.GameValidationService;
import com.dan.boardgame_cafe.utils.enums.GameCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameValidationService gameValidationService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    void createGameShouldPass(){
        //given
        GameDTO requestGameDto = GameDTO.builder()
                .gameName("Game 1")
                .minPlayers(2)
                .maxPlayers(4)
                .gameCategory(GameCategory.CARD_GAME)
                .build();

        GameDTO responseGameDto = GameDTO.builder()
                .id(1L)
                .gameName("Game 1")
                .minPlayers(2)
                .maxPlayers(4)
                .gameCategory(GameCategory.CARD_GAME)
                .build();

        Game game = Game.builder()
                .gameName("Game 1")
                .minPlayers(2)
                .maxPlayers(4)
                .gameCategory(GameCategory.CARD_GAME)
                .build();

        Game savedGame = Game.builder()
                .id(1L)
                .gameName("Game 1")
                .minPlayers(2)
                .maxPlayers(4)
                .gameCategory(GameCategory.CARD_GAME)
                .build();
        //when
        when(modelMapper.map(requestGameDto, Game.class)).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(savedGame);
        when(modelMapper.map(savedGame, GameDTO.class)).thenReturn(responseGameDto);

        GameDTO gameDTO = gameService.createGame(requestGameDto);
        //then
        verify(gameRepository, times(1)).save(game);
        assertNotNull(gameDTO.getId());
        assertEquals(requestGameDto.getGameName() ,gameDTO.getGameName());
    }
}

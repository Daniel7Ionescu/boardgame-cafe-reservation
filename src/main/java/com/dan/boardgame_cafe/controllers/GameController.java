package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.game.GameDTO;
import com.dan.boardgame_cafe.services.game.GameService;
import com.dan.boardgame_cafe.utils.enums.GameCategory;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(@RequestBody @Valid GameDTO gameDTO) {
        return ResponseEntity.ok(gameService.createGame(gameDTO));
    }

    @GetMapping
    public ResponseEntity<List<GameDTO>> geFilteredGames(@RequestParam(required = false) String inputName,
                                                         @RequestParam(required = false) GameCategory gameCategory,
                                                         @RequestParam(required = false) Integer minPlayers) {
        return ResponseEntity.ok(gameService.getFilteredGames(inputName, gameCategory, minPlayers));
    }

}

package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.game_table.GameTableCreateDTO;
import com.dan.boardgame_cafe.services.game_table.GameTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/game-tables")
public class GameTableController {

    private final GameTableService gameTableService;

    public GameTableController(GameTableService gameTableService) {
        this.gameTableService = gameTableService;
    }

    @PostMapping
    public ResponseEntity<GameTableCreateDTO> createGameTable(@RequestBody GameTableCreateDTO gameTableCreateDTO){
        return ResponseEntity.ok(gameTableService.createGameTable(gameTableCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<GameTableCreateDTO>> getAllGameTables(){
        return ResponseEntity.ok(gameTableService.getAllGameTables());
    }
}

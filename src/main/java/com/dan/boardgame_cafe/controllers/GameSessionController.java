package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.game_session.GameSessionDTO;
import com.dan.boardgame_cafe.models.dtos.game_session.GameSessionDetailDTO;
import com.dan.boardgame_cafe.services.game_session.GameSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class GameSessionController {

    private final GameSessionService gameSessionService;

    public GameSessionController(GameSessionService gameSessionService) {
        this.gameSessionService = gameSessionService;
    }

    @PostMapping("/from-reservation/{reservationId}")
    public ResponseEntity<GameSessionDTO> createSessionFromReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(gameSessionService.createSessionFromReservation(reservationId));
    }

    @GetMapping
    public ResponseEntity<List<GameSessionDTO>> getAllSessions(@RequestParam(required = false) Integer minPlayers,
                                                               @RequestParam(required = false) LocalDate localDate) {
        return ResponseEntity.ok(gameSessionService.getAllSessions(minPlayers, localDate));
    }

    @GetMapping("/{gameSessionId}")
    public ResponseEntity<GameSessionDetailDTO> getSessionById(@PathVariable Long gameSessionId){
        return ResponseEntity.ok(gameSessionService.getSessionById(gameSessionId));
    }

    @PutMapping("/{gameSessionId}")
    public ResponseEntity<GameSessionDetailDTO> endGameSession(@PathVariable Long gameSessionId){
        return ResponseEntity.ok(gameSessionService.endGameSession(gameSessionId));
    }

    @PutMapping("/{gameSessionId}/games/{gameId}")
    public ResponseEntity<GameSessionDetailDTO> addGameToSession(@PathVariable Long gameSessionId, @PathVariable Long gameId){
        return ResponseEntity.ok(gameSessionService.addGameToSession(gameSessionId, gameId));
    }
}

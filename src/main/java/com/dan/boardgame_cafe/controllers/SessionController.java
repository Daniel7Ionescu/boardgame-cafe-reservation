package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.session.SessionDTO;
import com.dan.boardgame_cafe.models.dtos.session.SessionDetailDTO;
import com.dan.boardgame_cafe.services.session.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/from-reservation/{reservationId}")
    public ResponseEntity<SessionDTO> createSessionFromReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(sessionService.createSessionFromReservation(reservationId));
    }

    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAllSessions(@RequestParam(required = false) Integer minPlayers,
                                                           @RequestParam(required = false) LocalDate localDate) {
        return ResponseEntity.ok(sessionService.getAllSessions(minPlayers, localDate));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDetailDTO> getSessionById(@PathVariable Long sessionId){
        return ResponseEntity.ok(sessionService.getSessionById(sessionId));
    }

    @PutMapping("/{sessionId}/games/{gameId}")
    public ResponseEntity<SessionDetailDTO> addGameToSession(@PathVariable Long sessionId, @PathVariable Long gameId){
        return ResponseEntity.ok(sessionService.addGameToSession(sessionId, gameId));
    }
}

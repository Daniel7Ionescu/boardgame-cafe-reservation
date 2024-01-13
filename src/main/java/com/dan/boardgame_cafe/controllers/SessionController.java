package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.session.SessionDTO;
import com.dan.boardgame_cafe.services.session.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/from-reservation/{reservationId}")
    public ResponseEntity<SessionDTO> createSessionFromReservation(@PathVariable Long reservationId){
        return ResponseEntity.ok(sessionService.createSessionFromReservation(reservationId));
    }
}

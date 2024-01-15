package com.dan.boardgame_cafe.services.session;

import com.dan.boardgame_cafe.models.dtos.session.SessionDTO;
import com.dan.boardgame_cafe.models.dtos.session.SessionDetailDTO;

import java.time.LocalDate;
import java.util.List;

public interface SessionService {

    SessionDTO createSessionFromReservation(Long reservationId);
    List<SessionDTO> getAllSessions(Integer minPlayers, LocalDate localDate);

    SessionDetailDTO getSessionById(Long sessionId);
    SessionDetailDTO addGameToSession(Long sessionId, Long gameId);
}

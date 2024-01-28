package com.dan.boardgame_cafe.services.game_session;

import com.dan.boardgame_cafe.models.dtos.game_session.GameSessionDTO;
import com.dan.boardgame_cafe.models.dtos.game_session.GameSessionDetailDTO;

import java.time.LocalDate;
import java.util.List;

public interface GameSessionService {

    GameSessionDTO createSessionFromReservation(Long reservationId);
    List<GameSessionDTO> getFilteredSessions(Integer minPlayers, LocalDate localDate);
    GameSessionDetailDTO getSessionById(Long gameSessionId);
    GameSessionDetailDTO addGameToSession(Long gameSessionId, Long gameId);
    GameSessionDetailDTO endGameSession(Long gameSessionId);
}

package com.dan.boardgame_cafe.services.session;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.session.SessionDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SessionService {

    SessionDTO createSessionFromReservation(Long reservationId);
    List<SessionDTO> getAllSessions(Integer minPlayers, LocalDate localDate);
}

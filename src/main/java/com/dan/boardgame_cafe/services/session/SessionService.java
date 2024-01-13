package com.dan.boardgame_cafe.services.session;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.session.SessionDTO;

public interface SessionService {

    SessionDTO createSessionFromReservation(Long reservationId);
}

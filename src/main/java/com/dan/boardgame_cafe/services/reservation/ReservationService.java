package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationCreateDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.models.entities.Session;

import java.util.List;

public interface ReservationService {

    ReservationCreateDTO createReservation(ReservationCreateDTO reservationCreateDTO);
    List<ReservationDTO> getAllReservations();

    ReservationDTO getReservationById(Long id);
    Reservation retrieveReservationById(Long id);
    void updatedReservationCreatedSession(Long id, Session session);
}

package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDetailDTO;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.models.entities.Session;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    ReservationDTO createReservation(ReservationDTO reservationDTO);
    List<ReservationDTO> getAllReservations(String lastName, ReservationStatus reservationStatus, LocalDate localDate);

    ReservationDetailDTO getReservationById(Long reservationId);
    Reservation retrieveReservationById(Long reservationId);
    Reservation retrieveReservationByIdWithValidStatusForSessionCreate(Long reservationId);
    void updateReservationCreatedSession(Long reservationId, Session session);
}

package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDetailDTO;
import com.dan.boardgame_cafe.models.entities.GameSession;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    ReservationDTO createReservation(ReservationDTO reservationDTO);
    List<ReservationDTO> getAllReservations(String lastName, ReservationStatus reservationStatus, LocalDate localDate);
    ReservationDetailDTO getReservationById(Long reservationId);
    void updatedReservationAfterGameSessionCreate(Long reservationId, GameSession gameSession);
    ReservationDetailDTO acceptReservation(Long reservationId, Long gameTableId);


    Reservation retrieveReservationById(Long reservationId);
    Reservation retrieveReservationByIdWithValidStatusForSessionCreate(Long reservationId);

}

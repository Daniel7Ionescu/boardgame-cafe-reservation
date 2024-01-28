package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationJoinDTO;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.dan.boardgame_cafe.utils.enums.ReservationType;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationValidationService {

    void validateReservationDTO(ReservationDTO reservationDTO);
    void validateReservationStatus(ReservationStatus inputStatus, ReservationStatus validStatus);
    void validateReservationType(ReservationType inputType, ReservationType validType);
    void validateUniqueReservation(String email, LocalDate reservationDate, LocalTime reservationTime);

    void validateReservationCanBeJoined(Reservation reservation);
}

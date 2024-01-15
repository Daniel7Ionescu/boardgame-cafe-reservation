package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDetailDTO;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;

public interface ReservationValidationService {

    void validateReservationDTO(ReservationDTO reservationDTO);
    void validateReservationStatus(ReservationStatus inputStatus, ReservationStatus validStatus);
}

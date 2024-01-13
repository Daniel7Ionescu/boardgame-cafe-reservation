package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationCreateDTO;

public interface ReservationValidationService {

    void validateReservationCreateDTO(ReservationCreateDTO reservationCreateDTO);
}

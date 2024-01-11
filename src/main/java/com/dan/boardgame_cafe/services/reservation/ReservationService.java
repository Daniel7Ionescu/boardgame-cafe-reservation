package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.ReservationDTO;

public interface ReservationService {

    ReservationDTO createReservation(ReservationDTO reservationDTO, Long customerId);
}

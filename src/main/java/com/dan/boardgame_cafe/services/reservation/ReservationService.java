package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationCreateDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;

import java.util.List;

public interface ReservationService {

//    ReservationDTO createReservation(ReservationDTO reservationDTO);
    ReservationCreateDTO createReservation(ReservationCreateDTO reservationCreateDTO);
    List<ReservationDTO> getAllReservations();
}

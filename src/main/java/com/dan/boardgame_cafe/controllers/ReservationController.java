package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.ReservationDTO;
import com.dan.boardgame_cafe.services.reservation.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable Long customerId){
        return ResponseEntity.ok(reservationService.createReservation(reservationDTO, customerId));
    }
}

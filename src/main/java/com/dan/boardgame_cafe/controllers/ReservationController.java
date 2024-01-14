package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationCreateDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.services.reservation.ReservationService;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationCreateDTO> createReservation(@Valid @RequestBody ReservationCreateDTO reservationCreateDTO) {
        return ResponseEntity.ok(reservationService.createReservation(reservationCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@RequestParam(required = false) String lastName,
                                                                   @RequestParam(required = false) ReservationStatus reservationStatus) {
        return ResponseEntity.ok(reservationService.getAllReservations(lastName, reservationStatus));
    }
}

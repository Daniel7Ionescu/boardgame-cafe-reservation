package com.dan.boardgame_cafe.controllers;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDetailDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationJoinDTO;
import com.dan.boardgame_cafe.services.reservation.ReservationService;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.createReservation(reservationDTO));
    }

    @PostMapping("/{eventId}/join")
    public ResponseEntity<ReservationJoinDTO> createReservationJoinEvent(@RequestBody ReservationJoinDTO reservationJoinDTO,
                                                                         @PathVariable Long eventId){
        return ResponseEntity.ok(reservationService.createReservationJoinEvent(reservationJoinDTO, eventId));
    }

    @PutMapping("/{eventId}/join/{reservationJoinId}")
    public ResponseEntity<ReservationDetailDTO> acceptJoinReservation(@PathVariable Long eventId,
                                                                      @PathVariable Long reservationJoinId){
        return ResponseEntity.ok(reservationService.acceptJoinReservation(reservationJoinId, eventId));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@RequestParam(required = false) String lastName,
                                                                   @RequestParam(required = false) ReservationStatus reservationStatus,
                                                                   @RequestParam(required = false) LocalDate localDate) {
        return ResponseEntity.ok(reservationService.getAllReservations(lastName, reservationStatus, localDate));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDetailDTO> getReservationById(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.getReservationById(reservationId));
    }

    @PutMapping("/{reservationId}/{gameTableId}")
    public ResponseEntity<ReservationDetailDTO> acceptReservation(@PathVariable Long reservationId,
                                                                  @PathVariable Long gameTableId){
        return ResponseEntity.ok(reservationService.acceptStandardReservation(reservationId, gameTableId));
    }
}

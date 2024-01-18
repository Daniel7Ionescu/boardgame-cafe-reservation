package com.dan.boardgame_cafe.services.game_table;

import com.dan.boardgame_cafe.exceptions.reservation.ReservationPartySizeOverTableCapacityException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationTimeOverlapsWithExistingSessionException;
import com.dan.boardgame_cafe.models.entities.GameSession;
import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class GameTableValidationServiceImpl implements GameTableValidationService {

    @Override
    public void validateGameTableCanAccommodateReservation(GameTable gameTable, Reservation inputRes) {
        if(inputRes.getPartySize() > gameTable.getTableCapacity()){
            throw new ReservationPartySizeOverTableCapacityException("Table capacity cannot accommodate");
        }

        long result = gameTable.getReservations().stream()
                .filter(reservation -> reservation.getReservationDate().equals(inputRes.getReservationDate()))
                .filter(reservation -> reservation.getReservationStatus().equals(ReservationStatus.ACCEPTED))
                .filter(reservation -> doTimesOverlap(
                        reservation.getReservationStartTime(),
                        reservation.getReservationEndTime(),
                        inputRes.getReservationStartTime(),
                        inputRes.getReservationEndTime()
                ))
                .count();

        if (result > 0) {
            throw new ReservationTimeOverlapsWithExistingSessionException("Resulting Session will overlap");
        }
    }

    private boolean doTimesOverlap(LocalTime sourceStart,
                                   LocalTime sourceEnd,
                                   LocalTime inputStart,
                                   LocalTime inputEnd) {
        return sourceStart.isBefore(inputEnd) && inputStart.isBefore(sourceEnd);

    }

}

package com.dan.boardgame_cafe.services.game_table;

import com.dan.boardgame_cafe.exceptions.general.DuplicateResourceException;
import com.dan.boardgame_cafe.exceptions.general.ResourceInvalidUsageException;
import com.dan.boardgame_cafe.models.dtos.game_table.GameTableCreateDTO;
import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.repositories.GameTableRepository;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class GameTableValidationServiceImpl implements GameTableValidationService {

    private final GameTableRepository gameTableRepository;

    public GameTableValidationServiceImpl(GameTableRepository gameTableRepository) {
        this.gameTableRepository = gameTableRepository;
    }

    @Override
    public void validateGameTable(GameTableCreateDTO gameTableCreateDTO) {
        long result = gameTableRepository.countByGameTableName(gameTableCreateDTO.getGameTableName());
        if (result > 0) {
            throw new DuplicateResourceException(
                    String.format("A table with name %s already exists.", gameTableCreateDTO.getGameTableName()));
        }
    }

    @Override
    public void validateGameTableCanAccommodateReservation(GameTable gameTable, Reservation inputRes) {
        if (inputRes.getPartySize() > gameTable.getTableCapacity()) {
            throw new ResourceInvalidUsageException("Table capacity cannot accommodate");
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
            throw new ResourceInvalidUsageException("Resulting GameSession time will overlap with existing GameSession");
        }
    }

    private boolean doTimesOverlap(LocalTime sourceStart,
                                   LocalTime sourceEnd,
                                   LocalTime inputStart,
                                   LocalTime inputEnd) {
        return sourceStart.isBefore(inputEnd) && inputStart.isBefore(sourceEnd);
    }
}

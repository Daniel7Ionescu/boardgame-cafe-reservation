package com.dan.boardgame_cafe.services.game_table;

import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.models.entities.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public interface GameTableValidationService {

    void validateGameTableCanAccommodateReservation(GameTable gameTable, Reservation reservation);
}

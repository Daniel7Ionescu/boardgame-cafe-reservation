package com.dan.boardgame_cafe.services.game_table;

import com.dan.boardgame_cafe.models.dtos.game_table.GameTableCreateDTO;
import com.dan.boardgame_cafe.models.dtos.game_table.GameTableDetailDTO;
import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.models.entities.Reservation;

import java.util.List;

public interface GameTableService {

    GameTableCreateDTO createGameTable(GameTableCreateDTO gameTableCreateDTO);
    List<GameTableCreateDTO> getAllGameTables();
    GameTableDetailDTO getGameTableById(Long gameTableId);
    GameTableDetailDTO addReservationToGameTable(Long gameTableId, Reservation reservation);
    GameTable retrieveGameTableById(Long gameTableId);
    GameTable retrieveGameTableThatCanAccommodateReservation(Long gameTableId, Reservation reservation);
}

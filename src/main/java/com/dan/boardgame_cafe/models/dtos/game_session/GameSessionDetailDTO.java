package com.dan.boardgame_cafe.models.dtos.game_session;

import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.models.entities.Reservation;
import lombok.Data;

import java.util.List;

@Data
public class GameSessionDetailDTO extends GameSessionDTO {

    private Long id;
    private List<Game> games;
    private GameTable gameTable;
    private List<Reservation> reservations;
}

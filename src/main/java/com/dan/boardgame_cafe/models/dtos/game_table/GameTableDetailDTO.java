package com.dan.boardgame_cafe.models.dtos.game_table;

import com.dan.boardgame_cafe.models.entities.Reservation;
import lombok.Data;

import java.util.List;


@Data
public class GameTableDetailDTO extends GameTableCreateDTO{

    private Long id;
    private List<Reservation> reservations;
}

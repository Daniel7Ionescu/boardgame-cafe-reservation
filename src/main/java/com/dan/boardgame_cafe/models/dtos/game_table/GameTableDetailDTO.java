package com.dan.boardgame_cafe.models.dtos.game_table;

import com.dan.boardgame_cafe.models.entities.Reservation;
import lombok.Data;

import java.util.List;


@Data
public class GameTableDetailDTO {

    private Long id;
    private String gameTableName;
    private Integer tableCapacity;
    private List<Reservation> reservations;
}

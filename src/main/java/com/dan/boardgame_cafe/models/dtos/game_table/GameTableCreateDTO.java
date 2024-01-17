package com.dan.boardgame_cafe.models.dtos.game_table;

import lombok.Data;


@Data
public class GameTableCreateDTO {

    private Long id;
    private String gameTableName;
    private Integer tableCapacity;
}

package com.dan.boardgame_cafe.models.dtos.game;

import com.dan.boardgame_cafe.utils.enums.GameCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GameDTO {

    private Long id;

    @NotBlank(message = "A name is required")
    @Size(min = 3, max = 50, message = "Invalid name length")
    private String gameName;

    @NotNull(message = "Minimum players number is required")
    @Min(value = 2, message = "Needs at least 2 players")
    private Integer minPlayers;

    @NotNull(message = "Maximum players number is required")
    private Integer maxPlayers;

    @NotNull(message = "Game category is required")
    private GameCategory gameCategory;

    private Double complexity;
}

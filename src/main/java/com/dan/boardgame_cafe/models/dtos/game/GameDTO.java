package com.dan.boardgame_cafe.models.dtos.game;

import com.dan.boardgame_cafe.utils.enums.GameCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.dan.boardgame_cafe.utils.constants.BusinessConstants.VALID_GAME_NAME_REGEX;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameDTO {

    private Long id;

    @NotBlank(message = "A name is required")
    @Size(min = 3, max = 50, message = "Invalid name length")
    @Pattern(regexp = VALID_GAME_NAME_REGEX, message = "Invalid characters")
    private String gameName;

    @NotNull(message = "Minimum players number is required")
    @Min(value = 2, message = "Needs at least 2 players")
    private Integer minPlayers;

    @NotNull(message = "Maximum players number is required")
    private Integer maxPlayers;

    @NotNull(message = "Game category is required")
    private GameCategory gameCategory;
}

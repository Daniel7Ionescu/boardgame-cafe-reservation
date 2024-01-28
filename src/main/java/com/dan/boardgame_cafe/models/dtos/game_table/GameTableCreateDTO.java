package com.dan.boardgame_cafe.models.dtos.game_table;

import jakarta.validation.constraints.*;
import lombok.Data;

import static com.dan.boardgame_cafe.utils.constants.BusinessConstants.VALID_GAME_NAME_REGEX;

@Data
public class GameTableCreateDTO {

    private Long id;

    @NotBlank(message = "Table name is required")
    @Size(min = 3, max = 50, message = "Invalid Name")
    @Pattern(regexp = VALID_GAME_NAME_REGEX, message = "Invalid characters")
    private String gameTableName;

    @NotNull
    @Min(value = 2, message = "At least 2 seats are required")
    @Max(value = 8, message = "Table capacity exceeded")
    private Integer tableCapacity;
}

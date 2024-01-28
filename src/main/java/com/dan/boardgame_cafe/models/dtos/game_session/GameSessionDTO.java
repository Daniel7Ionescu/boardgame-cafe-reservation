package com.dan.boardgame_cafe.models.dtos.game_session;

import com.dan.boardgame_cafe.utils.enums.GameSessionStatus;
import com.dan.boardgame_cafe.utils.enums.GameSessionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class GameSessionDTO {

    @NotBlank(message = "Session date is required")
    private LocalDate sessionDate;

    @NotBlank(message = "Session start time is required")
    private LocalTime sessionStart;

    @NotBlank(message = "Session start time is required")
    private LocalTime sessionEnd;

    @NotNull(message = "Session Status is required")
    private GameSessionStatus gameSessionStatus;

    @NotNull(message = "Session Type is required")
    private GameSessionType gameSessionType;
}

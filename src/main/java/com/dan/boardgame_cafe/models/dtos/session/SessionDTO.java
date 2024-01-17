package com.dan.boardgame_cafe.models.dtos.session;

import com.dan.boardgame_cafe.utils.enums.GameSessionStatus;
import com.dan.boardgame_cafe.utils.enums.SessionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SessionDTO {

    private Long id;

    @NotBlank(message = "Session name is required")
    private String sessionName;

    @NotBlank(message = "Session date is required")
    private LocalDate sessionDate;

    @NotBlank(message = "Session start time is required")
    private LocalTime sessionStart;

    @NotBlank
    private LocalTime sessionEnd;

    @NotNull
    @Min(value = 1)
    private Integer partySize;

    @NotNull(message = "Session Status is required")
    private GameSessionStatus gameSessionStatus;

    @NotNull(message = "Session Type is required")
    private SessionType sessionType;

    private Boolean sessionChildren;
}

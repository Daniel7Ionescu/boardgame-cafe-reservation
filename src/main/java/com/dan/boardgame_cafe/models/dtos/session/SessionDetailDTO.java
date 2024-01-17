package com.dan.boardgame_cafe.models.dtos.session;

import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.utils.enums.GameSessionStatus;
import com.dan.boardgame_cafe.utils.enums.SessionType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class SessionDetailDTO {

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

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
    private BigDecimal sessionCost;

    private List<Reservation> reservations;

    private List<Game> games;
}

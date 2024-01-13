package com.dan.boardgame_cafe.models.dtos.session;

import com.dan.boardgame_cafe.models.entities.Reservation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SessionDTO {

    private Long id;

    @NotBlank
    private String sessionName;

    @NotNull
    private LocalDateTime sessionStartTime;

    @NotNull
    private LocalDateTime sessionEndTime;

    @NotNull
    @Min(value = 1)
    private Integer partySize;

    private Integer sessionChildren;

    private List<Reservation> reservations;
}

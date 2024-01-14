package com.dan.boardgame_cafe.models.dtos.session;

import com.dan.boardgame_cafe.models.entities.Reservation;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SessionDTO {

    private Long id;

    @NotBlank
    private String sessionName;

    @NotBlank
    private LocalDate sessionDate;

    @NotBlank
    private LocalTime sessionStart;

    @NotBlank
    private LocalTime sessionEnd;

    @NotNull
    @Min(value = 1)
    private Integer partySize;

    private Boolean sessionChildren;

    private List<Reservation> reservations;
}

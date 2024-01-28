package com.dan.boardgame_cafe.models.dtos.reservation;

import com.dan.boardgame_cafe.models.entities.GameSession;
import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ReservationDetailDTO extends ReservationDTO{

    private Long id;
    private GameSession gameSession;
    private GameTable gameTable;
}

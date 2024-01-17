package com.dan.boardgame_cafe.models.dtos.day_schedule;

import com.dan.boardgame_cafe.models.entities.GameSession;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DayScheduleDetailDTO {

    private Long id;
    private LocalDate scheduleDate;
    private boolean hasSpecialEvents;
    private List<GameSession> scheduleGameSessions;
}

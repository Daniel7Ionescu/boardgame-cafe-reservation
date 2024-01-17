package com.dan.boardgame_cafe.models.dtos.day_schedule;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateDayScheduleDTO {

    private Long id;
    private LocalDate scheduleDate;
}

package com.dan.boardgame_cafe.models.dtos;

import com.dan.boardgame_cafe.models.entities.Customer;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {

    private Long id;

    @NotBlank
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotBlank
    @Min(value = 1,  message = "at least 1 adult is required")
    private Integer adults;

    private Integer children;

    private ReservationStatus reservationStatus;

    private Customer customer;
}

package com.dan.boardgame_cafe.models.dtos.reservation;

import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 50, message = "Invalid Name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 50, message = "Invalid Name")
    private String lastName;

    @NotBlank(message = "An email is required")
    @Email(message = "Invalid Email")
    private String email;

    @NotNull(message = "Date of birth is required")
    @Past
    private LocalDate dob;

    @NotBlank(message = "Starting time is required")
    @Future
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @NotBlank
    @Min(value = 1, message = "at least 1 adult is required")
    private Integer adults;

    private Integer children;

    private ReservationStatus reservationStatus;
}

package com.dan.boardgame_cafe.models.dtos.reservation;

import com.dan.boardgame_cafe.models.entities.GameSession;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class ReservationDetailDTO {

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

    @NotNull(message = "Reservation date is required")
    @Future
    private LocalDate reservationDate;

    @NotNull(message = "Start time is required")
    private LocalTime reservationStart;

    @NotNull(message = "End time is required")
    private LocalTime reservationEndTime;

    @NotBlank
    @Min(value = 1, message = "at least 1 adult is required")
    private Integer partySize;

    @NotNull(message = "Reservation status is required")
    private ReservationStatus reservationStatus;

    @NotNull(message = "Reservation type is required")
    private ReservationType reservationType;

    private GameSession gameSession;
}
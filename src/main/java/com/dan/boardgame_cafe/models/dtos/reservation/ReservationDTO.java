package com.dan.boardgame_cafe.models.dtos.reservation;

import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.dan.boardgame_cafe.utils.constants.BusinessConstants.VALID_NAME_REGEX;

@Data
public class ReservationDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 50, message = "Invalid Name")
    @Pattern(regexp = VALID_NAME_REGEX, message = "Invalid characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 50, message = "Invalid Name")
    @Pattern(regexp = VALID_NAME_REGEX, message = "Invalid characters")
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
    private LocalTime reservationStartTime;

    @NotNull(message = "End time is required")
    private LocalTime reservationEndTime;

    @NotNull(message = "Reservation type is required")
    private ReservationType reservationType;

    private ReservationStatus reservationStatus;

    @NotNull
    @Min(value = 1, message = "At least 1 party member is required")
    @Max(value = 9, message = "Party members limit exceeded")
    private Integer partySize;
}

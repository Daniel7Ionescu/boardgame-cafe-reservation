package com.dan.boardgame_cafe.models.dtos.reservation;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationCreateDTO {

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

    @NotNull(message = "Starting time is required")
    @Future
    private LocalDateTime startTime;

    @NotNull
    @Min(value = 1, message = "at least 1 party member is required")
    private Integer partySize;

    private Boolean hasChildren;
}

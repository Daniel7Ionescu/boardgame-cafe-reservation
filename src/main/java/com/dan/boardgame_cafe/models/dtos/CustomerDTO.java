package com.dan.boardgame_cafe.models.dtos;

import com.dan.boardgame_cafe.models.entities.Reservation;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 30, message = "must be between 3 and 30 characters")
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 30, message = "must be between 3 and 30 characters")
    private String lastName;

    @Size(max = 30, message = "maximum 30 characters")
    private String nickname;

    @NotBlank(message = "an email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 10, max = 17, message = "number should have at least 10 or less than 17 digits")
    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    @Past
    private LocalDate dob;

    private List<Reservation> customerReservations = new ArrayList<>();
}

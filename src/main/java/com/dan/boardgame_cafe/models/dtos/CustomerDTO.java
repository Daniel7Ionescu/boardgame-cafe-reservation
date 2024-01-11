package com.dan.boardgame_cafe.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

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
    @Email
    private String email;

    private String phoneNumber;


    private LocalDate dob;

    private Integer age;
}

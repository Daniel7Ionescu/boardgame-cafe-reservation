package com.dan.boardgame_cafe.models.dtos.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorDTO {

    private HttpStatus httpStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;

    public ErrorDTO(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
        timestamp = LocalDateTime.now();
    }
}

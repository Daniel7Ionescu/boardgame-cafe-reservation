package com.dan.boardgame_cafe.services.email;

import java.time.LocalDate;
import java.time.LocalTime;

public interface EmailService {

    void sendEmailEventCreationAccepted(String to, String name, LocalDate date, LocalTime time);
}

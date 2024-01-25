package com.dan.boardgame_cafe.services.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailEventCreationAccepted(String to, String name, LocalDate date, LocalTime time) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("inb3zna@gmail.com");
        message.setTo(to);
        message.setSubject("Special Event Accepted!");
        message.setText(
                "Congratulations "
                + name
                + ", your event for" + date
                + ", is set to start at: " + time + " !"
        );

        javaMailSender.send(message);
        log.info("Email sent to {} successfully!", to);
    }
}

package com.dan.boardgame_cafe.models.entities;

import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column (name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column (name = "email", length = 75)
    private String email;

    @Column (name = "date_of_birth", length = 50)
    private LocalDate dob;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_start_time", nullable = false)
    private LocalTime reservationStartTime;

    @Column(name = "reservation_end_time", nullable = false)
    private LocalTime reservationEndTime;

    @Column(name = "party_size")
    private Integer partySize;

    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;

    @Column(name = "reservation_type")
    private ReservationType reservationType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "game_table_id")
    private GameTable gameTable;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private GameSession gameSession;
}
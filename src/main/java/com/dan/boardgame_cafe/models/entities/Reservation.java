package com.dan.boardgame_cafe.models.entities;

import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "party_size")
    private Integer partySize;

    @Column(name = "has_children")
    private boolean hasChildren;

    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;

    @ManyToMany(mappedBy = "reservations")
    @JsonManagedReference
    private List<Session> sessions = new ArrayList<>();
}
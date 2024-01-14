package com.dan.boardgame_cafe.models.entities;

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
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_name", nullable = false)
    private String sessionName;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(name = "session_start", nullable = false)
    private LocalTime sessionStart;

    @Column(name = "session_end", nullable = false)
    private LocalTime sessionEnd;

    @Column(name = "party_size", nullable = false)
    private Integer partySize;

    @Column(name = "session_children")
    private Boolean hasChildren;

    @ManyToMany
    @JoinTable(name = "reservation_session",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    @JsonBackReference
    private List<Reservation> reservations = new ArrayList<>();
}

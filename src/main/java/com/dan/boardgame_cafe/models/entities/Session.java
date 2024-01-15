package com.dan.boardgame_cafe.models.entities;

import com.dan.boardgame_cafe.utils.enums.SessionStatus;
import com.dan.boardgame_cafe.utils.enums.SessionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
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

    @Column(name = "session_status", nullable = false)
    private SessionStatus sessionStatus;

    @Column(name = "session_type", nullable = false)
    private SessionType sessionType;

    @Column(name = "session_cost")
    private BigDecimal sessionCost;

    @ManyToMany
    @JoinTable(name = "reservations_sessions",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    @JsonBackReference
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "games_sessions",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    @JsonManagedReference
    private List<Game> games = new ArrayList<>();
}

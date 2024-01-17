package com.dan.boardgame_cafe.models.entities;

import com.dan.boardgame_cafe.utils.enums.GameSessionStatus;
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
public class GameSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(name = "session_start", nullable = false)
    private LocalTime sessionStart;

    @Column(name = "session_end", nullable = false)
    private LocalTime sessionEnd;

    @Column(name = "session_status", nullable = false)
    private GameSessionStatus gameSessionStatus;

    @OneToMany(mappedBy = "gameSession")
    @JsonBackReference
    private List<Reservation> reservations = new ArrayList<>();

    @JsonBackReference(value = "gameSessionsReference")
    @ManyToOne
    private GameTable gameTable;

    @ManyToMany
    @JoinTable(name = "games_played_in_sessions",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    @JsonManagedReference
    private List<Game> games = new ArrayList<>();
}

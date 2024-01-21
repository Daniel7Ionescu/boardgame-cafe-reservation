package com.dan.boardgame_cafe.models.entities;

import com.dan.boardgame_cafe.utils.enums.GameSessionStatus;
import com.dan.boardgame_cafe.utils.enums.GameSessionType;
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
@Table(name = "game_sessions")
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

    @Column(name = "session_type", nullable = false)
    private GameSessionType gameSessionType;

    @JsonBackReference
    @OneToMany(mappedBy = "gameSession")
    private List<Reservation> reservations = new ArrayList<>();

    @JsonBackReference(value = "gameSessionsReference")
    @ManyToOne
    private GameTable gameTable;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "games_played_in_sessions",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    private List<Game> games = new ArrayList<>();
}

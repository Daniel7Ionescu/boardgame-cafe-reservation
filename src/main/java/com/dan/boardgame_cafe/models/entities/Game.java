package com.dan.boardgame_cafe.models.entities;

import com.dan.boardgame_cafe.utils.enums.GameCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_name", unique = true)
    private String gameName;

    @Column(name = "min_players", nullable = false)
    private Integer minPlayers;

    @Column(name = "max_players", nullable = false)
    private Integer maxPlayers;

    @Column(name = "game_category")
    private GameCategory gameCategory;

    @Column(name = "complexity")
    private Double complexity;

    @ManyToMany(mappedBy = "games")
    @JsonBackReference
    private List<GameSession> gameSessions = new ArrayList<>();
}

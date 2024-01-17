package com.dan.boardgame_cafe.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "game_tables")
public class GameTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_table_name")
    private String gameTableName;

    @Column(name = "table_capacity")
    private Integer tableCapacity;

    @JsonManagedReference
    @OneToMany(mappedBy = "gameTable")
    private List<Reservation> reservations = new ArrayList<>();

    @JsonManagedReference(value = "gameSessionsReference")
    @OneToMany(mappedBy = "gameTable")
    private List<GameSession> gameSessions = new ArrayList<>();
}

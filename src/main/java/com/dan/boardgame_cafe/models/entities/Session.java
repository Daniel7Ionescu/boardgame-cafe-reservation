package com.dan.boardgame_cafe.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
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

    @Column(name = "session_start_time", nullable = false)
    private LocalDateTime sessionStartTime;

    @Column(name = "session_end_time")
    private LocalDateTime sessionEndTime;

    @Column(name = "party_size")
    private Integer partySize;

    @Column(name = "session_children")
    private Integer sessionChildren;

    @ManyToMany
    @JoinTable(name = "reservation_session",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "session_id"))
    @JsonBackReference
    private List<Reservation> reservations = new ArrayList<>();
}

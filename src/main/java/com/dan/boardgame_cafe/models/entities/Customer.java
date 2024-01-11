package com.dan.boardgame_cafe.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column (name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column (name = "nickname", length = 50)
    private String nickname;

    @Column (name = "email", unique = true, length = 75)
    private String email;

    @Column (name = "phone_number", length = 50)
    private String phoneNumber;

    @Column (name = "date_of_birth", length = 50)
    private LocalDate dob;

    @Column (name = "age")
    private Integer age;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Reservation> customerReservations = new ArrayList<>();
}
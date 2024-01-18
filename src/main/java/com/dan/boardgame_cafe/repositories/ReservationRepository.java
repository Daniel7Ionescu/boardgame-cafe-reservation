package com.dan.boardgame_cafe.repositories;

import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.models.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    Long countByEmailAndReservationDateAndReservationStartTime(String email,
                                                               LocalDate reservationDate,
                                                               LocalTime reservationStartTime);

    List<Reservation> findAllByReservationDateAndReservationStartTimeAndGameTable(LocalDate reservationDate,
                                                                                  LocalTime reservationStartTime,
                                                                                  GameTable gameTable);
}

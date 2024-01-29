package com.dan.boardgame_cafe.utils.specifications;

import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ReservationSpecification {

    private ReservationSpecification() {
    }

    public static Specification<Reservation> lastNameLike(String lastName) {
        return (root, query, builder) -> builder
                .like(root.get("lastName"), "%" + lastName + "%");

    }

    public static Specification<Reservation> hasReservationStatus(ReservationStatus reservationStatus) {
        return (root, query, builder) -> builder
                .equal(root.get("reservationStatus"), reservationStatus.getReservationStatusLabel());
    }

    public static Specification<Reservation> hasReservationType(ReservationType reservationType) {
        return (root, query, builder) -> builder
                .equal(root.get("reservationType"), reservationType.getReservationTypeLabel());
    }

    public static Specification<Reservation> reservationOnDate(LocalDate localDate){
        return ((root, query, builder) -> builder
                .equal(root.get("reservationDate"), localDate));
    }
}

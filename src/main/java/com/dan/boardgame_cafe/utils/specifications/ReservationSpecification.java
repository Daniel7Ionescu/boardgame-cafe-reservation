package com.dan.boardgame_cafe.utils.specifications;

import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import org.springframework.data.jpa.domain.Specification;

public class ReservationSpecification {

    private ReservationSpecification(){}

    public static Specification<Reservation> lastNameLike(String lastName) {
        return (root, query, builder) -> builder.
                like(root.get("lastName"), "%" + lastName + "%");
    }

    public static Specification<Reservation> hasReservationStatus(ReservationStatus reservationStatus){
        return (root, query, builder) -> builder
                .equal(root.get("reservationStatus"),reservationStatus.getReservationStatusLabel());
    }
}

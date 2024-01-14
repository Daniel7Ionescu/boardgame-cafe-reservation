package com.dan.boardgame_cafe.utils.specifications;

import com.dan.boardgame_cafe.models.entities.Reservation;
import org.springframework.data.jpa.domain.Specification;

public class ReservationSpecification {

    private ReservationSpecification(){}

    public static Specification<Reservation> firstNameLike(String firstNameLike) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get("firstName"), "%" + firstNameLike + "%"));
    }
}

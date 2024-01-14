package com.dan.boardgame_cafe.utils.specifications;

import com.dan.boardgame_cafe.models.entities.Session;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
public class SessionSpecification {

    private SessionSpecification() {
    }

    public static Specification<Session> partySizeFilter(Integer minPlayers) {
        return ((root, query, builder) -> builder
                .greaterThanOrEqualTo(root.get("partySize"), minPlayers));
    }

    public static Specification<Session> isSessionOnDate(LocalDate localDate) {
        return ((root, query, builder) -> builder
                .equal(root.get("sessionDate"), localDate));
    }

    public static Specification<Session> sessionToday() {
        return ((root, query, builder) -> {
            LocalDate dateToday = LocalDate.now();
            return builder.equal(root.get("sessionDate"), dateToday);
        });
    }
}

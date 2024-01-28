package com.dan.boardgame_cafe.utils.specifications;

import com.dan.boardgame_cafe.models.entities.GameSession;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
public class SessionSpecification {

    private SessionSpecification() {
    }

    public static Specification<GameSession> partySizeFilter(Integer minPlayers) {
        return ((root, query, builder) -> builder
                .greaterThanOrEqualTo(root.get("partySize"), minPlayers));
    }

    public static Specification<GameSession> isSessionOnDate(LocalDate localDate) {
        return ((root, query, builder) -> builder
                .equal(root.get("sessionDate"), localDate));
    }
}

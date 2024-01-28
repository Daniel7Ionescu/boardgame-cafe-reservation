package com.dan.boardgame_cafe.utils.specifications;

import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.utils.enums.GameCategory;
import org.springframework.data.jpa.domain.Specification;

public class GameSpecification {

    private GameSpecification() {}

    public static Specification<Game> gameNameLike(String inputName) {
        return (root, query, builder) -> builder
                .like(root.get("gameName"), "%" + inputName + "%");
    }

    public static Specification<Game> hasGameCategory(GameCategory gameCategory) {
        return (root, query, builder) -> builder
                .equal(root.get("gameCategory"), gameCategory.getGameCategoryLabel());
    }

    public static Specification<Game> gamePlayers(Integer minPlayers) {
        return ((root, query, builder) -> builder
                .greaterThanOrEqualTo(root.get("minPlayers"), minPlayers));
    }
}
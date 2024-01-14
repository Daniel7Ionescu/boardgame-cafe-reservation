package com.dan.boardgame_cafe.repositories;

import com.dan.boardgame_cafe.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    long countByGameName(String name);
}

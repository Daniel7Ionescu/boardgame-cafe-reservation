package com.dan.boardgame_cafe.repositories;

import com.dan.boardgame_cafe.models.entities.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameTableRepository extends JpaRepository<GameTable, Long> {

    long countByGameTableName(String name);
}

package com.dan.boardgame_cafe.repositories;

import com.dan.boardgame_cafe.models.entities.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long>, JpaSpecificationExecutor<GameSession> {
}

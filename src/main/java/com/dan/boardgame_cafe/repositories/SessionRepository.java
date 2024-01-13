package com.dan.boardgame_cafe.repositories;

import com.dan.boardgame_cafe.models.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}

package com.dan.boardgame_cafe.services.session;

import com.dan.boardgame_cafe.exceptions.ResourceNotFoundException;
import com.dan.boardgame_cafe.models.dtos.session.SessionDTO;
import com.dan.boardgame_cafe.models.dtos.session.SessionDetailDTO;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.models.entities.GameSession;
import com.dan.boardgame_cafe.repositories.SessionRepository;
import com.dan.boardgame_cafe.services.game.GameService;
import com.dan.boardgame_cafe.services.reservation.ReservationService;
import com.dan.boardgame_cafe.utils.enums.GameSessionStatus;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.dan.boardgame_cafe.utils.specifications.SessionSpecification.*;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    private final ReservationService reservationService;
    private final GameService gameService;

    public SessionServiceImpl(SessionRepository sessionRepository, ModelMapper modelMapper, ReservationService reservationService, GameService gameService) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
        this.reservationService = reservationService;
        this.gameService = gameService;
    }

    @Override
    public List<SessionDTO> getAllSessions(Integer minPlayers, LocalDate localDate) {
        Specification<GameSession> sessionFilter = Specification
                .where(minPlayers == null ? null : partySizeFilter(minPlayers))
                .and(localDate== null ? null : isSessionOnDate(localDate));

        return sessionRepository.findAll(sessionFilter).stream()
                .map(session -> modelMapper.map(session, SessionDTO.class))
                .toList();
    }

    @Override
    public SessionDetailDTO getSessionById(Long sessionId) {
        return modelMapper.map(retrieveSessionById(sessionId), SessionDetailDTO.class);
    }

    @Transactional
    @Override
    public SessionDTO createSessionFromReservation(Long reservationId) {
        Reservation reservation = reservationService.retrieveReservationByIdWithValidStatusForSessionCreate(reservationId);
        GameSession gameSession = buildSessionFromReservation(reservation);
        GameSession savedGameSession = sessionRepository.save(gameSession);
        log.info("Session id: {} saved in DB", savedGameSession.getId());
        reservationService.updatedReservationAfterGameSessionCreate(reservationId, savedGameSession);

        return modelMapper.map(savedGameSession, SessionDTO.class);
    }

    @Transactional
    @Override
    public SessionDetailDTO addGameToSession(Long sessionId, Long gameId) {
        GameSession gameSession = sessionRepository.findById(sessionId).orElseThrow(
                () -> new ResourceNotFoundException("Session with id: " + sessionId + " not found"));
        Game game = gameService.retrieveGameById(gameId);
        gameSession.getGames().add(game);
//        session.getGames().add(gameService.retrieveGameById(gameId));
        GameSession savedGameSession = sessionRepository.save(gameSession);
        log.info("Session id: {} has been added game id: {} and saved in DB", savedGameSession.getId(), gameId);

        return modelMapper.map(savedGameSession, SessionDetailDTO.class);
    }

    private GameSession buildSessionFromReservation(Reservation reservation){
        GameSession gameSession = new GameSession();
        gameSession.setSessionDate(reservation.getReservationDate());
        gameSession.setSessionStart(reservation.getReservationStartTime());
        gameSession.setSessionEnd(reservation.getReservationEndTime());
        gameSession.getReservations().add(reservation);
        gameSession.setGameSessionStatus(GameSessionStatus.ACTIVE);
        gameSession.setGameTable(reservation.getGameTable());
        log.info("Session from Reservation successfully built");

        return gameSession;
    }

    private GameSession retrieveSessionById(Long sessionId){
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session with id: " + sessionId + " not found"));
    }
}

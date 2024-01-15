package com.dan.boardgame_cafe.services.session;

import com.dan.boardgame_cafe.exceptions.ResourceNotFoundException;
import com.dan.boardgame_cafe.models.dtos.session.SessionDTO;
import com.dan.boardgame_cafe.models.dtos.session.SessionDetailDTO;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.models.entities.Session;
import com.dan.boardgame_cafe.repositories.SessionRepository;
import com.dan.boardgame_cafe.services.game.GameService;
import com.dan.boardgame_cafe.services.reservation.ReservationService;
import com.dan.boardgame_cafe.utils.enums.SessionStatus;
import com.dan.boardgame_cafe.utils.enums.SessionType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.dan.boardgame_cafe.utils.constants.BusinessConstants.*;
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
        Specification<Session> sessionFilter = Specification
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
        Session session = buildSessionFromReservation(reservation);
        Session savedSession = sessionRepository.save(session);
        log.info("Session id: {} saved in DB", savedSession.getId());
        reservationService.updateReservationCreatedSession(reservationId, session);

        return modelMapper.map(savedSession, SessionDTO.class);
    }

    @Transactional
    @Override
    public SessionDetailDTO addGameToSession(Long sessionId, Long gameId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
                () -> new ResourceNotFoundException("Session with id: " + sessionId + " not found"));
        Game game = gameService.retrieveGameById(gameId);
        session.getGames().add(game);
//        session.getGames().add(gameService.retrieveGameById(gameId));
        Session savedSession = sessionRepository.save(session);
        log.info("Session id: {} has been added game id: {} and saved in DB", savedSession.getId(), gameId);

        return modelMapper.map(savedSession, SessionDetailDTO.class);
    }

    private Session buildSessionFromReservation(Reservation reservation){
        Session session = new Session();
        session.setSessionName(reservation.getFirstName() + " " +
                LocalTime.from(reservation.getReservationStart()));
        session.setSessionDate(reservation.getReservationDate());
        session.setSessionStart(reservation.getReservationStart());
        session.setSessionEnd(reservation.getReservationStart().plusMinutes(MINIMUM_SESSION_MINUTES));
        session.setPartySize(reservation.getPartySize());
        session.getReservations().add(reservation);
        session.setSessionStatus(SessionStatus.READY);
        session.setSessionType(SessionType.STANDARD);

        return session;
    }

    private Session retrieveSessionById(Long sessionId){
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session with id: " + sessionId + " not found"));
    }
}

package com.dan.boardgame_cafe.services.game_session;

import com.dan.boardgame_cafe.exceptions.general.ResourceHasInvalidPropertiesException;
import com.dan.boardgame_cafe.exceptions.general.ResourceNotFoundException;
import com.dan.boardgame_cafe.models.dtos.game_session.GameSessionDTO;
import com.dan.boardgame_cafe.models.dtos.game_session.GameSessionDetailDTO;
import com.dan.boardgame_cafe.models.entities.Game;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.models.entities.GameSession;
import com.dan.boardgame_cafe.repositories.GameSessionRepository;
import com.dan.boardgame_cafe.services.game.GameService;
import com.dan.boardgame_cafe.services.reservation.ReservationService;
import com.dan.boardgame_cafe.utils.enums.GameSessionStatus;
import com.dan.boardgame_cafe.utils.enums.GameSessionType;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
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
public class GameSessionServiceImpl implements GameSessionService {

    private final GameSessionRepository gameSessionRepository;
    private final ModelMapper modelMapper;
    private final ReservationService reservationService;
    private final GameService gameService;
    private final GameSessionValidationService gameSessionValidationService;

    public GameSessionServiceImpl(GameSessionRepository gameSessionRepository,
                                  ModelMapper modelMapper,
                                  ReservationService reservationService,
                                  GameService gameService,
                                  GameSessionValidationService gameSessionValidationService) {
        this.gameSessionRepository = gameSessionRepository;
        this.modelMapper = modelMapper;
        this.reservationService = reservationService;
        this.gameService = gameService;
        this.gameSessionValidationService = gameSessionValidationService;
    }

    /**
     * Attempts to create a GameSession entity from an existing Reservation
     * @param reservationId the Reservation with status ACCEPTED
     * @return dto of the saved GameSession
     */
    @Transactional
    @Override
    public GameSessionDetailDTO createSessionFromReservation(Long reservationId) {
        Reservation reservation = reservationService.retrieveReservationByIdForSessionCreation(reservationId);
        GameSession gameSession = buildSessionFromReservation(reservation);
        GameSession savedGameSession = gameSessionRepository.save(gameSession);
        log.info("Session id: {} saved in DB", savedGameSession.getId());
        reservationService.updatedReservationAfterGameSessionCreate(reservationId, savedGameSession);

        return modelMapper.map(savedGameSession, GameSessionDetailDTO.class);
    }

    @Override
    public List<GameSessionDTO> getFilteredSessions(Integer minPlayers, LocalDate localDate) {
        Specification<GameSession> sessionFilter = Specification
                .where(minPlayers == null ? null : partySizeFilter(minPlayers))
                .and(localDate== null ? null : isSessionOnDate(localDate));

        return gameSessionRepository.findAll(sessionFilter).stream()
                .map(session -> modelMapper.map(session, GameSessionDTO.class))
                .toList();
    }

    @Override
    public GameSessionDetailDTO getSessionById(Long sessionId) {
        return modelMapper.map(retrieveGameSessionById(sessionId), GameSessionDetailDTO.class);
    }

    @Transactional
    @Override
    public GameSessionDetailDTO addGameToSession(Long gameSessionId, Long gameId) {
        GameSession gameSession = retrieveGameSessionById(gameSessionId);
        Game game = gameService.retrieveGameById(gameId);
        gameSessionValidationService.validateGameSessionCanAddGame(gameSession, game);
        gameSession.getGames().add(game);
        GameSession savedGameSession = gameSessionRepository.save(gameSession);
        log.info("Session id: {} has added game id: {} and saved in DB", savedGameSession.getId(), gameId);

        return modelMapper.map(savedGameSession, GameSessionDetailDTO.class);
    }

    @Transactional
    @Override
    public GameSessionDetailDTO endGameSession(Long gameSessionId) {
        GameSession gameSession = retrieveGameSessionById(gameSessionId);
        if(!gameSession.getGameSessionStatus().equals(GameSessionStatus.ACTIVE)){
            throw new ResourceHasInvalidPropertiesException(
                    String.format("GameSession id: %d has invalid status", gameSession.getId()));
        }
        gameSession.setGameSessionStatus(GameSessionStatus.ENDED);
        GameSession savedGameSession = gameSessionRepository.save(gameSession);
        log.info("Game session id: {} status set to: {}", savedGameSession.getId(), savedGameSession.getGameSessionStatus());

        return modelMapper.map(savedGameSession, GameSessionDetailDTO.class);
    }

    private GameSession buildSessionFromReservation(Reservation reservation){
        GameSession gameSession = new GameSession();
        gameSession.setSessionDate(reservation.getReservationDate());
        gameSession.setSessionStart(reservation.getReservationStartTime());//normally would be LocalTime.now()
        gameSession.setSessionEnd(reservation.getReservationEndTime());
        gameSession.getReservations().add(reservation);
        gameSession.setGameSessionStatus(GameSessionStatus.ACTIVE);
        gameSession.setGameSessionType(getGameSessionTypeBasedOnReservationType(reservation));
        gameSession.setGameTable(reservation.getGameTable());
        log.info("Session from Reservation id: {} successfully built", reservation.getId());

        return gameSession;
    }

    private GameSession retrieveGameSessionById(Long sessionId){
        return gameSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("GameSession id: %d not found", sessionId)));

    }

    private GameSessionType getGameSessionTypeBasedOnReservationType(Reservation reservation){
        if(reservation.getReservationType().equals(ReservationType.STANDARD)){
            return GameSessionType.STANDARD;
        } else if (reservation.getReservationType().equals(ReservationType.CREATE_EVENT)) {
            return GameSessionType.EVENT;
        } else {
            throw new ResourceHasInvalidPropertiesException("Invalid Reservation Type");
        }
    }
}

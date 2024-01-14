package com.dan.boardgame_cafe.services.session;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.session.SessionDTO;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.models.entities.Session;
import com.dan.boardgame_cafe.repositories.SessionRepository;
import com.dan.boardgame_cafe.services.reservation.ReservationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.dan.boardgame_cafe.utils.constants.SessionConstants.*;
import static com.dan.boardgame_cafe.utils.specifications.SessionSpecification.*;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    private final ReservationService reservationService;

    public SessionServiceImpl(SessionRepository sessionRepository, ModelMapper modelMapper, ReservationService reservationService) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
        this.reservationService = reservationService;
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

    @Transactional
    @Override
    public SessionDTO createSessionFromReservation(Long reservationId) {
//        ReservationDTO reservationDTO = reservationService.getReservationById(reservationId);
        Reservation reservation = reservationService.retrieveReservationById(reservationId);
        //validate reservation status to be PENDING
        //check for reservation day & hours not to overlap with existing sessions
        Session session = buildSessionFromReservation(reservation);
        Session savedSession = sessionRepository.save(session);
        reservationService.updatedReservationCreatedSession(reservationId, savedSession);

        return modelMapper.map(savedSession, SessionDTO.class);
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

        return session;
    }
}

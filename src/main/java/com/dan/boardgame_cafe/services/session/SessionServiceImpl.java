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
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.dan.boardgame_cafe.utils.constants.SessionConstants.*;

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
                LocalTime.from(reservation.getStartTime()));
        session.setSessionStartTime(reservation.getStartTime());
        session.setSessionEndTime(reservation.getStartTime().plusMinutes(MINIMUM_SESSION_MINUTES));
        session.setPartySize(reservation.getPartySize());
        session.getReservations().add(reservation);

        return session;
    }
}

package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.exceptions.ResourceHasInvalidStatusOrTypeException;
import com.dan.boardgame_cafe.exceptions.reservation.DuplicateReservationException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationInvalidAgeException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationOutsideWorkingHoursException;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationJoinDTO;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.repositories.ReservationRepository;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import static com.dan.boardgame_cafe.utils.constants.BusinessConstants.*;

@Service
public class ReservationValidationServiceImpl implements ReservationValidationService {

    private final ReservationRepository reservationRepository;

    public ReservationValidationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void validateReservationDTO(ReservationDTO reservationDTO) {
        validateUniqueReservation(
                reservationDTO.getEmail(),
                reservationDTO.getReservationDate(),
                reservationDTO.getReservationStartTime());
        LocalDate dateNow = LocalDate.now();
        Integer customerAge = Period.between(reservationDTO.getDob(), dateNow).getYears();

        validateReservationCreatorIsOfAge(customerAge);
        validateReservationTime(reservationDTO.getReservationStartTime());
    }

    @Override
    public void validateReservationStatus(ReservationStatus inputStatus, ReservationStatus validStatus) {
        if (!inputStatus.equals(validStatus)) {
            throw new ResourceHasInvalidStatusOrTypeException("Reservation has invalid status");
        }
    }

    @Override
    public void validateReservationType(ReservationType inputType, ReservationType validType) {
        if (!inputType.equals(validType)) {
            throw new ResourceHasInvalidStatusOrTypeException("Reservation has invalid type");
        }
    }

    @Override
    public void validateUniqueReservation(String email, LocalDate reservationDate, LocalTime reservationTime) {
        Long result = reservationRepository.countByEmailAndReservationDateAndReservationStartTime(
                email,
                reservationDate,
                reservationTime
        );

        if (result > 0) {
            throw new DuplicateReservationException("This Reservation already exists");
        }
    }

    @Override
    public void validateReservationCanBeJoined(Reservation reservation) {
        if (!reservation.getReservationType().equals(ReservationType.CREATE_EVENT)) {
            throw new ResourceHasInvalidStatusOrTypeException("Reservation has invalid Type");
        }
        if (!reservation.getReservationStatus().equals(ReservationStatus.ACCEPTED)) {
            throw new ResourceHasInvalidStatusOrTypeException("Reservation has invalid Status");
        }

    }

    private void validateReservationCreatorIsOfAge(Integer customerAge) {
        if (customerAge < MINIMUM_AGE || customerAge > MAXIMUM_AGE) {
            throw new ReservationInvalidAgeException("Invalid age");
        }
    }

    private void validateReservationTime(LocalTime reservationStart) {
        if (reservationStart.isBefore(LocalTime.parse(OPENING_HOURS))) {
            throw new ReservationOutsideWorkingHoursException("Reservation is outside working hours");
        }
        if (reservationStart.plusMinutes(MINIMUM_SESSION_MINUTES).isAfter(LocalTime.parse(CLOSING_HOURS))) {
            throw new ReservationOutsideWorkingHoursException("Reservation is outside working hours");
        }
    }
}

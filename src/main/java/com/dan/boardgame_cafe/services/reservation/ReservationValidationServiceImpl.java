package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.exceptions.ResourceHasInvalidStatusException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationInvalidAgeException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationOutsideWorkingHoursException;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import static com.dan.boardgame_cafe.utils.constants.BusinessConstants.*;

@Service
public class ReservationValidationServiceImpl implements ReservationValidationService{

    @Override
    public void validateReservationDTO(ReservationDTO reservationDTO) {
        LocalDate dateNow = LocalDate.now();
        Integer customerAge = Period.between(reservationDTO.getDob(), dateNow).getYears();

        validateReservationCreatorIsOfAge(customerAge);
        validateReservationTime(reservationDTO.getReservationStartTime());
    }

    @Override
    public void validateReservationStatus(ReservationStatus inputStatus, ReservationStatus validStatus) {
        if(!inputStatus.equals(validStatus)){
            throw new ResourceHasInvalidStatusException("Reservation has invalid status");
        }
    }

    private void validateReservationCreatorIsOfAge(Integer customerAge){
        if(customerAge < MINIMUM_AGE || customerAge > MAXIMUM_AGE){
            throw new ReservationInvalidAgeException("Invalid age");
        }
    }

    private void validateReservationTime(LocalTime reservationStart) {
        if(reservationStart.isBefore(LocalTime.parse(OPENING_HOURS))){
            throw new ReservationOutsideWorkingHoursException("Reservation is outside working hours");
        }
        if(reservationStart.plusMinutes(MINIMUM_SESSION_MINUTES).isAfter(LocalTime.parse(CLOSING_HOURS))){
            throw new ReservationOutsideWorkingHoursException("Reservation is outside working hours");
        }
    }
}

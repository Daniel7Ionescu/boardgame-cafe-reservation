package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.exceptions.reservation.ReservationInvalidAgeException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationOutsideWorkingHoursException;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationCreateDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import static com.dan.boardgame_cafe.utils.constants.SessionConstants.*;

@Service
public class ReservationValidationServiceImpl implements ReservationValidationService{

    @Override
    public void validateReservationCreateDTO(ReservationCreateDTO reservationCreateDTO) {
        LocalDate dateNow = LocalDate.now();
        Integer customerAge = Period.between(reservationCreateDTO.getDob(), dateNow).getYears();

        validateReservationCreatorIsOfAge(customerAge);
        validateReservationTime(reservationCreateDTO.getReservationStart());
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

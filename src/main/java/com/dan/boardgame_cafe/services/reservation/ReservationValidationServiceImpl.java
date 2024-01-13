package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.exceptions.reservation.ReservationInvalidAgeException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationMinimumDurationException;
import com.dan.boardgame_cafe.exceptions.reservation.ReservationOutsideWorkingHoursException;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationCreateDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import static com.dan.boardgame_cafe.utils.constants.SessionConstants.*;

@Service
public class ReservationValidationServiceImpl implements ReservationValidationService{

//    private final Integer MINIMUM_AGE = 16;
//    private final Integer MAXIMUM_AGE = 90;
//    private final String OPENING_HOURS = "11:00";
//    private final String CLOSING_HOURS = "23:00";
//    private final Long MINIMUM_SESSION_MINUTES = 179L;


    @Override
    public void validateReservationCreateDTO(ReservationCreateDTO reservationCreateDTO) {
        LocalDate dateNow = LocalDate.now();
        Integer customerAge = Period.between(reservationCreateDTO.getDob(), dateNow).getYears();
        validateReservationCreatorIsOfAge(customerAge);

        LocalTime reservationStart = reservationCreateDTO.getStartTime().toLocalTime();
        validateReservationTime(reservationStart);
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

//    private void validateMinimumReservationDuration(LocalTime reservationStart, LocalTime reservationEnd) {
//        if(!reservationEnd.isAfter(reservationStart.plusMinutes(MINIMUM_SESSION_MINUTES))){
//            throw new ReservationMinimumDurationException("Invalid reservation minimum duration");
//        }
//    }
}

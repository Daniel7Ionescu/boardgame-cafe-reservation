package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.ReservationDTO;
import com.dan.boardgame_cafe.models.entities.Customer;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.repositories.ReservationRepository;
import com.dan.boardgame_cafe.services.customer.CustomerService;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ModelMapper modelMapper, CustomerService customerService) {
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO, Long customerId) {
        //to do: validate reservation
        //validate customer

        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        Customer customer = customerService.retrieveCustomerById(customerId);
        reservation.setCustomer(customer);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        Reservation savedReservation = reservationRepository.save(reservation);

        return modelMapper.map(savedReservation, ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .toList();
    }
}

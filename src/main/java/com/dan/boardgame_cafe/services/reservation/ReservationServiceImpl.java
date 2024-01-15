package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.exceptions.ResourceNotFoundException;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDetailDTO;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.models.entities.Session;
import com.dan.boardgame_cafe.repositories.ReservationRepository;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.dan.boardgame_cafe.utils.specifications.ReservationSpecification.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    private final ReservationValidationService reservationValidationService;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ModelMapper modelMapper, ReservationValidationService reservationValidationService) {
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
        this.reservationValidationService = reservationValidationService;
    }

    @Transactional
    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        reservationValidationService.validateReservationDTO(reservationDTO);
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        Reservation savedReservation = reservationRepository.save(reservation);
        log.info("Reservation with id: {} saved in database", savedReservation.getId());

        return modelMapper.map(savedReservation, ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getAllReservations(String lastName, ReservationStatus reservationStatus, LocalDate localDate) {
        Specification<Reservation> resFilter = Specification
                .where(lastName == null ? null : lastNameLike(lastName))
                .and(reservationStatus == null ? null : hasReservationStatus(reservationStatus))
                .and(localDate == null ? null : reservationOnDate(localDate));

        return reservationRepository.findAll(resFilter).stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .toList();
    }

    @Override
    public ReservationDetailDTO getReservationById(Long id) {
        return modelMapper.map(retrieveReservationById(id), ReservationDetailDTO.class);
    }

    @Override
    public void updateReservationCreatedSession(Long reservationId, Session session) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with id: " + reservationId + " not found"));
        reservation.setReservationStatus(ReservationStatus.APPROVED);

        Reservation savedReservation = reservationRepository.save(reservation);
        log.info("Reservation id: {} status updated to {} and saved in DB", savedReservation.getId(), savedReservation.getReservationStatus());
    }

    @Override
    public Reservation retrieveReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation with id: " + id + " not found"));
    }

    @Override
    public Reservation retrieveReservationByIdWithValidStatusForSessionCreate(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new ResourceNotFoundException("Reservation with id: " + reservationId + " not found"));
        reservationValidationService.validateReservationStatus(reservation.getReservationStatus(), ReservationStatus.PENDING);

        return reservation;
    }
}

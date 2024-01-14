package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.models.dtos.reservation.ReservationCreateDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.models.entities.Session;
import com.dan.boardgame_cafe.repositories.ReservationRepository;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.dan.boardgame_cafe.utils.specifications.ReservationSpecification.firstNameLike;

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
    public ReservationCreateDTO createReservation(ReservationCreateDTO reservationCreateDTO) {
        reservationValidationService.validateReservationCreateDTO(reservationCreateDTO);
        Reservation reservation = modelMapper.map(reservationCreateDTO, Reservation.class);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        Reservation savedReservation = reservationRepository.save(reservation);
        log.info("Reservation with id: {} saved in database", savedReservation.getId());

        return modelMapper.map(savedReservation, ReservationCreateDTO.class);
    }

//    @Override
//    public List<ReservationDTO> getAllReservations() {
//        return reservationRepository.findAll().stream()
//                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
//                .toList();
//    }

    @Override
    public List<ReservationDTO> getAllReservations(String firstNameLikeFilter) {
        Specification<Reservation> resFilters = Specification.where(
                firstNameLikeFilter == null ? null : firstNameLike(firstNameLikeFilter)
        );

        return reservationRepository.findAll(resFilters).stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDTO.class))
                .toList();
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();

        return modelMapper.map(reservation, ReservationDTO.class);
    }

    @Override
    public void updatedReservationCreatedSession(Long id, Session session) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        reservation.setReservationStatus(ReservationStatus.APPROVED);
        reservationRepository.save(reservation);
    }

    @Override
    public Reservation retrieveReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow();
    }
}

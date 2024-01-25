package com.dan.boardgame_cafe.services.reservation;

import com.dan.boardgame_cafe.exceptions.ResourceNotFoundException;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationDetailDTO;
import com.dan.boardgame_cafe.models.dtos.reservation.ReservationJoinDTO;
import com.dan.boardgame_cafe.models.entities.GameSession;
import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.repositories.ReservationRepository;
import com.dan.boardgame_cafe.services.email.EmailService;
import com.dan.boardgame_cafe.services.game_table.GameTableService;
import com.dan.boardgame_cafe.utils.enums.ReservationStatus;
import com.dan.boardgame_cafe.utils.enums.ReservationType;
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
    private final GameTableService gameTableService;
    private final EmailService emailService;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  ModelMapper modelMapper,
                                  ReservationValidationService reservationValidationService,
                                  GameTableService gameTableService, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.modelMapper = modelMapper;
        this.reservationValidationService = reservationValidationService;
        this.gameTableService = gameTableService;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        reservationValidationService.validateReservationDTO(reservationDTO);
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setReservationStatus(ReservationStatus.PENDING);
        reservation.setReservationType(reservationDTO.getReservationType());
        Reservation savedReservation = reservationRepository.save(reservation);
        log.info("Reservation with id: {} saved in database", savedReservation.getId());

        return modelMapper.map(savedReservation, ReservationDTO.class);
    }

    /**
     * Creates a Reservation that is tied to a previously created Reservation
     * @param reservationJoinDTO the new Reservation that joins
     * @param reservationEventId the existing Reservation that can be joined
     * @return dto of the joining Reservation
     */
    @Transactional
    @Override
    public ReservationJoinDTO createReservationJoinEvent(ReservationJoinDTO reservationJoinDTO, Long reservationEventId) {
        reservationValidationService.validateReservationType(
                reservationJoinDTO.getReservationType(),
                ReservationType.JOIN_EVENT);

        Reservation reservationEventCreator = retrieveReservationById(reservationEventId);
        reservationValidationService.validateReservationCanBeJoined(reservationEventCreator);
        reservationValidationService.validateUniqueReservation(
                reservationJoinDTO.getEmail(),
                reservationEventCreator.getReservationDate(),
                reservationEventCreator.getReservationStartTime());

        Reservation reservationJoinEvent = modelMapper.map(reservationJoinDTO, Reservation.class);
        reservationJoinEvent.setReservationDate(reservationEventCreator.getReservationDate());
        reservationJoinEvent.setReservationStartTime(reservationEventCreator.getReservationStartTime());
        reservationJoinEvent.setReservationEndTime(reservationEventCreator.getReservationEndTime());
        reservationJoinEvent.setReservationStatus(ReservationStatus.PENDING);
        Reservation savedReservation = reservationRepository.save(reservationJoinEvent);
        log.info("Reservation type: {}  id: {} saved in DB", savedReservation.getReservationType(), savedReservation.getId());

        return modelMapper.map(savedReservation, ReservationJoinDTO.class);
    }

    @Override
    public List<ReservationDTO> getFilteredReservations(String lastName, ReservationStatus reservationStatus, LocalDate localDate) {
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

    /**
     * Assigns a Table to a Reservation with status PENDING, updating its status to ACCEPTED
     * sends a confirmation email if Reservation has type CREATE_EVENT
     * @param reservationId the Reservation that will be updated
     * @param gameTableId the Table that will be assigned
     * @return dto with properties updated
     */
    @Transactional
    @Override
    public ReservationDetailDTO acceptStandardReservation(Long reservationId, Long gameTableId) {
        Reservation reservation = retrieveReservationById(reservationId);
        reservationValidationService.validateReservationStatus(reservation.getReservationStatus(), ReservationStatus.PENDING);
        GameTable gameTable = gameTableService.retrieveGameTableThatCanAccommodateReservation(gameTableId, reservation);
        reservation.setGameTable(gameTable);
        reservation.setReservationStatus(ReservationStatus.ACCEPTED);
        Reservation savedReservation = reservationRepository.save(reservation);

        if(savedReservation.getReservationType().equals(ReservationType.CREATE_EVENT)){
            emailService.sendEmailEventCreationAccepted(
                    reservation.getEmail(),
                    reservation.getLastName(),
                    reservation.getReservationDate(),
                    reservation.getReservationStartTime());
        }

        return modelMapper.map(savedReservation, ReservationDetailDTO.class);
    }

    /**
     * Adds the joinReservation to a Table based on creatorReservation
     * @param joinReservationId the PENDING Reservation that wants to join
     * @param creatorReservationId the ACCEPTED Reservation that dictates the Table
     * @return dto of the joinReservation
     */

    @Transactional
    @Override
    public ReservationDetailDTO acceptJoinReservation(Long joinReservationId, Long creatorReservationId) {
        Reservation joinReservation = retrieveReservationById(joinReservationId);
        Reservation creatorReservation = retrieveReservationById(creatorReservationId);

        joinReservation.setReservationStatus(ReservationStatus.ACCEPTED);
        joinReservation.setGameTable(creatorReservation.getGameTable());

        Reservation savedReservation = reservationRepository.save(joinReservation);

        return modelMapper.map(savedReservation, ReservationDetailDTO.class);
    }

    /**
     * Updates the status of the Reservation to SERVICED after a GameSession has been created from it
     * if the Reservation created an event, all the related Reservations are updated as well
     * @param reservationId the Reservation that creates the GameSession
     * @param gameSession the resulting GameSession
     */
    @Override
    public void updatedReservationAfterGameSessionCreate(Long reservationId, GameSession gameSession) {
        Reservation reservation = retrieveReservationById(reservationId);
        reservation.setReservationStatus(ReservationStatus.SERVICED);
        reservation.setGameSession(gameSession);
        Reservation savedReservation = reservationRepository.save(reservation);
        log.info("Reservation id: {} status updated to {} and saved in DB", savedReservation.getId(), savedReservation.getReservationStatus());

        if(reservation.getReservationType().equals(ReservationType.CREATE_EVENT)){
            updateAllJoinedReservationsStatus(reservation, ReservationStatus.SERVICED);
        }
    }

    @Override
    public Reservation retrieveReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation with id: " + id + " not found"));
    }

    @Override
    public Reservation retrieveReservationByIdForSessionCreation(Long reservationId) {
        Reservation reservation = retrieveReservationById(reservationId);
        reservationValidationService.validateReservationStatus(reservation.getReservationStatus(), ReservationStatus.ACCEPTED);
        return reservation;
    }

    private void updateAllJoinedReservationsStatus(Reservation reservation, ReservationStatus reservationStatus){
        reservationRepository.findAllByReservationDateAndReservationStartTimeAndGameTable(
                        reservation.getReservationDate(),
                        reservation.getReservationStartTime(),
                        reservation.getGameTable()
                )
                .forEach(foundReservation -> {
                    foundReservation.setReservationStatus(reservationStatus);
                    reservationRepository.save(foundReservation);
                    log.info("Bulk - Reservation id: {} status updated", foundReservation.getId());
                } );
    }
}

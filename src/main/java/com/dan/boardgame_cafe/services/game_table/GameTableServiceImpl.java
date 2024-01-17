package com.dan.boardgame_cafe.services.game_table;

import com.dan.boardgame_cafe.exceptions.ResourceNotFoundException;
import com.dan.boardgame_cafe.models.dtos.game_table.GameTableCreateDTO;
import com.dan.boardgame_cafe.models.dtos.game_table.GameTableDetailDTO;
import com.dan.boardgame_cafe.models.entities.GameTable;
import com.dan.boardgame_cafe.models.entities.Reservation;
import com.dan.boardgame_cafe.repositories.GameTableRepository;
import com.dan.boardgame_cafe.services.reservation.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GameTableServiceImpl implements GameTableService {

    private final GameTableRepository gameTableRepository;
    private final ModelMapper modelMapper;
    private final GameTableValidationService gameTableValidationService;

    public GameTableServiceImpl(GameTableRepository gameTableRepository, ModelMapper modelMapper, GameTableValidationService gameTableValidationService) {
        this.gameTableRepository = gameTableRepository;
        this.modelMapper = modelMapper;
        this.gameTableValidationService = gameTableValidationService;
    }

    @Override
    public GameTableCreateDTO createGameTable(GameTableCreateDTO gameTableCreateDTO) {
        //validate dto
        GameTable savedGameTable = gameTableRepository.save(modelMapper.map(gameTableCreateDTO, GameTable.class));

        return modelMapper.map(savedGameTable, GameTableCreateDTO.class);
    }

    @Override
    public List<GameTableCreateDTO> getAllGameTables() {
        return gameTableRepository.findAll().stream()
                .map(gameTable -> modelMapper.map(gameTable, GameTableCreateDTO.class))
                .toList();
    }

    @Override
    public GameTableDetailDTO addReservationToGameTable(Long gameTableId, Reservation reservation) {
        GameTable gameTable = retrieveGameTableById(gameTableId);
        gameTableValidationService.validateGameTableCanAccommodateReservation(gameTable, reservation);
//        gameTable.getReservations().add(reservation);
        GameTable savedGameTable = gameTableRepository.save(gameTable);

        return modelMapper.map(savedGameTable, GameTableDetailDTO.class);
    }

    @Override
    public GameTable retrieveGameTableThatCanAccommodateReservation(Long gameTableId, Reservation reservation) {
        GameTable gameTable = retrieveGameTableById(gameTableId);
        gameTableValidationService.validateGameTableCanAccommodateReservation(gameTable, reservation);

        return gameTable;
    }

    @Override
    public GameTable retrieveGameTableById(Long gameTableId) {
        return gameTableRepository.findById(gameTableId)
                .orElseThrow(() -> new ResourceNotFoundException("Game table id:" + gameTableId + " not found"));
    }
}

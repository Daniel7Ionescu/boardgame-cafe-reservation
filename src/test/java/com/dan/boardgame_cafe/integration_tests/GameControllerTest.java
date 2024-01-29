package com.dan.boardgame_cafe.integration_tests;

import com.dan.boardgame_cafe.models.dtos.game.GameDTO;
import com.dan.boardgame_cafe.utils.enums.GameCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createGameTestShouldPass() throws Exception {
        GameDTO gameDTO = GameDTO.builder()
                .gameName("Game 1")
                .minPlayers(2)
                .maxPlayers(4)
                .gameCategory(GameCategory.CARD_GAME)
                .build();

        MvcResult createGameResult = mockMvc.perform(post("/api/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String gameResultAsString = createGameResult.getResponse().getContentAsString();
        GameDTO gameDTOConverted = objectMapper.readValue(gameResultAsString, GameDTO.class);

        assertEquals(gameDTO.getGameName(), gameDTOConverted.getGameName());
        assertEquals(gameDTO.getGameCategory(), gameDTOConverted.getGameCategory());
    }
}

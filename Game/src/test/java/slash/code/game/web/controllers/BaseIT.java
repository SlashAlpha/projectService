package slash.code.game.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import slash.code.game.model.CardRepository;
import slash.code.game.model.GameRepository;
import slash.code.game.model.PlayRepository;
import slash.code.game.model.PlayerRepository;
import slash.code.game.service.CardService;
import slash.code.game.service.GameService;
import slash.code.game.service.PlayService;
import slash.code.game.service.PlayerService;
import slash.code.game.viewModel.Mapper;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest
public class BaseIT {
    @Autowired
    WebApplicationContext wac;
    MockMvc mvc;
    @MockBean
    CardRepository cardRepository;
    @MockBean
    GameRepository gameRepository;
    @MockBean
    PlayerRepository playerRepository;
    @MockBean
    PlayRepository playRepository;
    @MockBean
    PlayerService playerService;
    @MockBean
    Mapper mapper;
    @MockBean
    PlayService playService;
    @MockBean
    CardService cardService;
    @MockBean
    GameService gameService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }
}

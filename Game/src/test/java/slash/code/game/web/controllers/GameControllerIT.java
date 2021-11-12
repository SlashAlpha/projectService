package slash.code.game.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GameControllerIT extends BaseIT {


    @WithMockUser("slash")
    @Test
    void getPlay() throws Exception {
        mvc.perform(get("/api/v1/poker/newplay"))
                .andExpect(status().isOk());


    }

    @Test
    void getPlayWithHttpBasic() throws Exception {
        mvc.perform(get("/api/v1/poker/newplay").with(httpBasic("user", "user")))
                .andExpect(status().isOk());


    }
}

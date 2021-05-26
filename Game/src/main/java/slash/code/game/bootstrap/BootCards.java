package slash.code.game.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import slash.code.game.service.CardService;

@Component
public class BootCards implements CommandLineRunner {
    CardService cardService;

    public BootCards(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public void run(String... args) throws Exception {
       cardService.getOnePokerCard();
    }
}

package slash.code.dealer.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import slash.code.dealer.deck.*;

@Component
public class boostrap implements CommandLineRunner {


    DealerRepository dealerRepository;
    GameRepository gameRepository;
    PlayerRepository playerRepository;

    public boostrap( DealerRepository dealerRepository, GameRepository gameRepository, PlayerRepository playerRepository) {

        this.dealerRepository = dealerRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        dealerRepository.deleteAll();


//        gameRepository.deleteAll();
//        playerRepository.deleteAll();

        System.out.println("DONE");


    }
}

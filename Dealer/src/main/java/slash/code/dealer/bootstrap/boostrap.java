package slash.code.dealer.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import slash.code.dealer.deck.*;

@Component
public class boostrap implements CommandLineRunner {


    DealerRepository dealerRepository;


    public boostrap( DealerRepository dealerRepository) {

        this.dealerRepository = dealerRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        dealerRepository.deleteAll();


//        gameRepository.deleteAll();
//        playerRepository.deleteAll();

        System.out.println("DONE");


    }
}

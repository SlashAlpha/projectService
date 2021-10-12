package slash.code.game.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import slash.code.game.model.*;
import slash.code.game.service.CardService;
import slash.code.game.service.GameService;
import slash.code.game.service.PlayerService;
import slash.code.game.service.PlayService;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class BootCards implements CommandLineRunner {
    CardService cardService;
    PlayerService playerService;
    GameService gameService;
    PlayService playService;
    PlayRepository playRepository;
    GameRepository gameRepository;
    CardRepository cardRepository;
    PlayerRepository playerRepository;

    public BootCards(CardService cardService, PlayerService playerService, GameService gameService, PlayService playService, PlayRepository playRepository, GameRepository gameRepository, CardRepository cardRepository, PlayerRepository playerRepository) {
        this.cardService = cardService;
        this.playerService = playerService;
        this.gameService = gameService;
        this.playService = playService;
        this.playRepository = playRepository;
        this.gameRepository = gameRepository;
        this.cardRepository = cardRepository;
        this.playerRepository = playerRepository;
    }

    @Override

    public void run(String... args) throws Exception {
//
        playRepository.deleteAll();
        gameRepository.deleteAll();
        playerRepository.deleteAll();
        cardRepository.deleteAll();



   //System.out.println(cardService.getOnePokerCard());

//            Player player=new Player();
//            ;
//            UUID playId=playService.newPlay();
//            playerService.newPlayer("Clement",38,5000);
//
//        playService.addPLayerToPlay(playId,  playerService.newPlayer("Pedro",32,6000).getId());
//
//
//        playService.addPLayerToPlay(playId,  playerService.newPlayer("Tabatha",34,8000).getId());
//        playService.addPLayerToPlay(playId,  playerService.newPlayer("Manuel",37,5000).getId());
//        playService.addPLayerToPlay(playId,  playerService.newPlayer("Samantha",31,4000).getId());





     //   Play play =new Play(new ArrayList<Game>(),new ArrayList<Player>(),0);
//        playRepository.save(play);
//        playerService.newPlayer("Diego",38,3000);
//        playerService.newPlayer("Pedro",32,6000);
//        playerService.newPlayer("Tabatha",34,8000);
//        playerService.newPlayer("Manuel",37,5000);
//        playerService.newPlayer("Rodrigo",31,4000);
//
//
//        playService.addPlayers(play);
//        System.out.println(play.getId());
//        Game game= playService.newGame(play);
//        playService.saveGame(game);
//        playService.addNewPlayer(play);
//        playRepository.save(play);

     //  Game game= playService.newGame(play);


//       gameService.blindToPlayers(game,play.getPlayers());
//            playRepository.save(play);
////      Game game1= playService.newGame(play);
////
////       playService.saveGame(game1);
////
////       gameService.blindToPlayers(game1,play.getPlayers());
////        playRepository.save(play);
//
//            gameService.cardDispatch(play.getPlayers());
//
//            gameService.riverCards1(game);
//           playService.saveGame(game);
//           gameService.riverCard2(game);
//           gameService.riverCard3(game);
//           playService.saveGame(game);
//














    }
}

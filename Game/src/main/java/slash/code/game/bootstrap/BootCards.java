package slash.code.game.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import slash.code.game.model.CardRepository;
import slash.code.game.model.GameRepository;
import slash.code.game.model.PlayRepository;
import slash.code.game.model.PlayerRepository;
import slash.code.game.service.*;
import slash.code.game.user.Role;
import slash.code.game.user.RoleRepository;
import slash.code.game.user.User;
import slash.code.game.user.UserRepository;

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
    UserService userService;
    UserRepository userRepository;
    RoleRepository roleRepository;

    public BootCards(CardService cardService, PlayerService playerService, GameService gameService, PlayService playService, PlayRepository playRepository, GameRepository gameRepository, CardRepository cardRepository, PlayerRepository playerRepository, UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.cardService = cardService;
        this.playerService = playerService;
        this.gameService = gameService;
        this.playService = playService;
        this.playRepository = playRepository;
        this.gameRepository = gameRepository;
        this.cardRepository = cardRepository;
        this.playerRepository = playerRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override

    public void run(String... args) throws Exception {
//
        playRepository.deleteAll();
        gameRepository.deleteAll();
        playerRepository.deleteAll();
        cardRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();


        //System.out.println(cardService.getOnePokerCard());


//            ;
        //       UUID playId=playService.newPlay();
        userService.saveRole(new Role("ROLE_USER"));
        userService.saveRole(new Role("ROLE_ADMIN"));
        userService.saveRole(new Role("ROLE_MANAGER"));
        userService.saveRole(new Role("ROLE_SUPER_ADMIN"));

        userService.saveUser(User.builder().email("johnbenett@gmail.com").firstName("John").lastName("Benett").password("1234").build());
        userService.saveUser(User.builder().email("rebeccacrawford@gmail.com").firstName("Rebecca").lastName("Crawford").password("1234").build());
        userService.saveUser(User.builder().email("maxwellLedoux@gmail.com").firstName("Maxwell").lastName("Ledoux").password("1234").build());
        userService.saveUser(User.builder().email("LindaSue@gmail.com").firstName("Linda").lastName("Sue").password("1234").build());
        userService.addRoleToUser("johnbenett@gmail.com", "ROLE_USER");
        userService.addRoleToUser("rebeccacrawford@gmail.com", "ROLE_ADMIN");
        userService.addRoleToUser("rebeccacrawford@gmail.com", "ROLE_MANAGER");

        userService.addRoleToUser("maxwellLedoux@gmail.com", "ROLE_MANAGER");
        userService.addRoleToUser("LindaSue@gmail.com", "ROLE_SUPER_ADMIN");

//        Player player=new Player();
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

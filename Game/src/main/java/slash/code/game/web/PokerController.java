package slash.code.game.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import slash.code.game.model.*;
import slash.code.game.service.CardService;
import slash.code.game.service.GameService;
import slash.code.game.service.PlayService;
import slash.code.game.viewModel.CardDTO;
import slash.code.game.viewModel.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/poker")
public class PokerController {

    CardService cardService;
    Mapper mapper;
    GameRepository gameRepository;
    GameService gameService;
    PlayService playService;
   private UUID playId;


    public PokerController(CardService cardService, Mapper mapper, GameRepository gameRepository, GameService gameService, PlayService playService) {
        this.cardService = cardService;
        this.mapper = mapper;
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.playService = playService;
    }




    @GetMapping("/riverfirstwave{gameId}")
    List<CardDTO> setRiver(@PathVariable UUID gameId){
     Game game =gameRepository.getById(gameId);
        List<Card> cards=new ArrayList<>();
        List<UUID> cardsId=gameService.riverCards1(game);
        List<CardDTO> cardsDto=new ArrayList<>();
        for (UUID id:cardsId
             ) {
            Card card=cardService.getOneCard(id);
            cards.add(card);
            cardsDto.add(mapper.convertToCardDTO(card));


        }



        return cardsDto;
    }

    @GetMapping("/riversecondwave{gameId}")
    public CardDTO riverSecondWave(@PathVariable String gameId){
        Game game =gameRepository.getById(UUID.fromString(gameId));
        Card card= cardService.getOneCard(gameService.riverCard2(game));
        game.getCards().add(card);

        return mapper.convertToCardDTO(card);

    }
    @GetMapping("/riverthirdwave{gameId}")
    public CardDTO riverThirdWave(@PathVariable UUID gameId){
        Game game =gameRepository.getById(gameId);
        Card card= cardService.getOneCard(gameService.riverCard3(game));


        return mapper.convertToCardDTO(card);

    }
    @GetMapping("/newplay")
    ResponseEntity<UUID> newPlay(){
        Play play = new Play(new ArrayList<Game>(), new ArrayList<Player>(),0);
        return new ResponseEntity<UUID>(playService.newPlay(), HttpStatus.OK);
        }

    @GetMapping("/newgame{playId}")
    public ResponseEntity<UUID> newGame(@PathVariable String playId) throws InterruptedException {
        System.out.println(playId);
  Game game;

        System.out.println(playId);
        if(!playId.isEmpty()){

     game= playService.newGame(UUID.fromString(playId));

      System.out.println("game created");
            return  new ResponseEntity<UUID>(game.id,HttpStatus.OK);
  }
else {
      System.out.println("failed to create game");
            return  null;
  }



    }

    @GetMapping("/player{playerId}")
    public ResponseEntity< List<CardDTO>> cardPLayers(@PathVariable String playerId){
        List<Card> cards=new ArrayList<>();
        try {
            cards= gameService.cardDispatch(UUID.fromString(playerId));

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
       List<CardDTO> cardDTOS=new ArrayList<>();
       if(!cards.isEmpty()){
        for (Card card :
              cards  ) {
            cardDTOS.add(mapper.convertToCardDTO(card));
        }

           return new ResponseEntity<List<CardDTO>>(cardDTOS, HttpStatus.OK);
       }

       cardDTOS=new ArrayList<>();
       cardDTOS.add(new CardDTO("notfound",0,0,"not found",0));
            return new ResponseEntity<List<CardDTO>>(cardDTOS,HttpStatus.FORBIDDEN);
    }


}
